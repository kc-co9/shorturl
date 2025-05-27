package com.co.kc.shortening.application.client;

/**
 * ID生成客户端
 *
 * @param <T> 返回类型
 * @author kc
 */
public interface IdClient<T> {
    /**
     * 下一个节点
     *
     * @return <T>ID生成结果
     */
    T next();
}
