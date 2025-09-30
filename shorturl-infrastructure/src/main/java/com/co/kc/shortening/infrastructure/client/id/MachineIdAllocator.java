package com.co.kc.shortening.infrastructure.client.id;

import com.co.kc.shortening.common.exception.ExhaustionException;
import com.co.kc.shortening.common.utils.NetworkUtils;
import com.co.kc.shortening.infrastructure.constant.ZooKeeperConstants;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * 雪花ID机器ID分配器
 *
 * @author kc
 */
public class MachineIdAllocator {
    private static final Logger LOG = LoggerFactory.getLogger(MachineIdAllocator.class);

    private final long maxMachineId;
    private final String machineIpv4;
    private final String datacenterIdPath;
    private final CuratorFramework zkCurator;
    private final String businessCode;

    @Getter
    private final long datacenterId;
    private long machineId = -1L;

    public MachineIdAllocator(CuratorFramework zkCurator, long dataCenterId, long maxMachineId) {
        this(zkCurator, dataCenterId, maxMachineId, "DEFAULT");
    }

    public MachineIdAllocator(CuratorFramework zkCurator, long dataCenterId, long maxMachineId, String businessCode) {
        this.zkCurator = zkCurator;
        this.datacenterId = dataCenterId;
        this.maxMachineId = maxMachineId;
        this.businessCode = businessCode;
        this.machineIpv4 = NetworkUtils.getFirstLocalIpv4();
        if (StringUtils.isBlank(machineIpv4)) {
            throw new IllegalArgumentException("无法获取有效的本机IPv4地址");
        }

        // 机器ID的路径
        this.datacenterIdPath = ZKPaths.makePath(ZooKeeperConstants.getSnowflakePath(), ZooKeeperConstants.getDataCenterPath(dataCenterId));

        // 初始化机器ID
        this.initMachineId();
        // 注册ZooKeeper连接状态监听器
        this.registerConnectionListener();
    }

    public synchronized long getMachineId() {
        if (machineId == -1L) {
            throw new IllegalStateException("初始化机器ID失败");
        }
        return machineId;
    }

    private void initMachineId() {
        boolean execSuc = refreshMachineId();
        if (!execSuc) {
            throw new ExhaustionException("machineId已经耗尽");
        }
    }

    @SneakyThrows
    public synchronized boolean refreshMachineId() {
        zkCurator.createContainers(datacenterIdPath);

        List<String> childrenPath = zkCurator.getChildren().forPath(datacenterIdPath);
        for (int machineId = 0; machineId <= maxMachineId; machineId++) {
            try {
                String childPath = businessCode + "_" + machineId;
                String machineIdPath = ZKPaths.makePath(datacenterIdPath, childPath);
                if (!childrenPath.contains(childPath)) {
                    zkCurator.create()
                            .withMode(CreateMode.EPHEMERAL)
                            .forPath(machineIdPath, machineIpv4.getBytes(StandardCharsets.UTF_8));
                    this.machineId = machineId;
                    return true;
                }
            } catch (KeeperException.NodeExistsException ex) {
                LOG.info("分配机器ID失败, ex:{}", ex.getMessage(), ex);
                childrenPath = zkCurator.getChildren().forPath(datacenterIdPath);
            }
        }
        return false;
    }

    /**
     * 主动释放机器ID（优雅下线）
     */
    @SneakyThrows
    public synchronized void releaseMachineId() {
        if (this.machineId != -1) {
            String childPath = businessCode + "_" + machineId;
            String machineIdPath = ZKPaths.makePath(datacenterIdPath, childPath);
            if (zkCurator.checkExists().forPath(machineIdPath) != null) {
                zkCurator.delete().forPath(machineIdPath);
            }
            this.machineId = -1;
        }
    }

    /**
     * 注册ZooKeeper连接状态监听器
     */
    private void registerConnectionListener() {
        zkCurator.getConnectionStateListenable()
                .addListener((client, newState) -> {
                    switch (newState) {
                        case RECONNECTED:
                        case LOST:
                            // 重连后刷新机器ID
                            if (this.machineIdHasChanged()) {
                                this.refreshMachineId();
                            }
                            break;
                    }
                });
    }

    @SneakyThrows
    private boolean machineIdHasChanged() {
        if (machineId == -1L) {
            return true;
        }
        String machineIdPath = ZKPaths.makePath(datacenterIdPath, businessCode + "_" + machineId);
        byte[] machineIdData = zkCurator.getData().forPath(machineIdPath);
        return machineIdData == null || !machineIpv4.equals(new String(machineIdData, StandardCharsets.UTF_8));
    }
}
