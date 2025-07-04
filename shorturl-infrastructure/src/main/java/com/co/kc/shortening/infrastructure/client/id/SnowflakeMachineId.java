package com.co.kc.shortening.infrastructure.client.id;

import com.co.kc.shortening.common.exception.ExhaustionException;
import com.co.kc.shortening.infrastructure.constant.ZooKeeperConstants;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;

import java.util.List;

/**
 * 雪花ID机器ID分配器
 *
 * @author kc
 */
public class SnowflakeMachineId {
    private final long maxMachineId;
    private final String machineIdRoot;
    private final CuratorFramework zkCurator;

    public SnowflakeMachineId(CuratorFramework zkCurator, long dataCenterId, long maxMachineId) {
        this.zkCurator = zkCurator;
        this.maxMachineId = maxMachineId;
        this.machineIdRoot = ZKPaths.makePath(ZooKeeperConstants.getSnowflakePath(), ZooKeeperConstants.getDataCenterPath(dataCenterId));
    }

    public synchronized long allocateMachineId() throws Exception {
        zkCurator.createContainers(machineIdRoot);

        List<String> childrenPath = zkCurator.getChildren().forPath(machineIdRoot);
        for (int machineId = 0; machineId <= maxMachineId; machineId++) {
            String machineIdPath = String.valueOf(machineId);
            if (!childrenPath.contains(machineIdPath)) {
                try {
                    zkCurator.create()
                            .withMode(CreateMode.EPHEMERAL)
                            .forPath(ZKPaths.makePath(machineIdRoot, machineIdPath));
                    return machineId;
                } catch (KeeperException.NodeExistsException ignored) {
                }
            }
        }

        throw new ExhaustionException("machineId已经耗尽");
    }
}
