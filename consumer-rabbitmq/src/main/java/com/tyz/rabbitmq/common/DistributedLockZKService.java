package com.tyz.rabbitmq.common;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * @program: cloud-demo
 * @description: zk distributed Lock Service
 * @author: tyz
 * @create: 2025-04-23
 */
//@Component
public class DistributedLockZKService {
    /**
     * <p>
     * Curator 统一封闭zk的类
     * </p>
     */
    private final CuratorFramework curatorFramework;

    /**
     * <p>
     * 分布式锁的路径的前缀
     * </p>
     */
    private static final String LOCK_PATH = "/locks";

    /**
     * <p>
     * 日志信息
     * </p>
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLockZKService.class);

    @Autowired
    public DistributedLockZKService(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }


    public <R> R executeWithLock(String lockKey, Object event, Function<Object, R> task) {
        InterProcessMutex lock = new InterProcessMutex(curatorFramework, LOCK_PATH + "/" + lockKey);
        try {
            if (lock.acquire(10, TimeUnit.SECONDS)) {
                // 尝试获取锁，最多等待10秒
                LOGGER.info("zk获取锁成功true");
                return task.apply(event);
            } else {
                LOGGER.info("zk释放锁false");
                throw new RuntimeException("获取锁false");
            }
        } catch (Exception e) {
            throw new RuntimeException("获取锁异常失败", e);
        } finally {
            try {
                // 释放锁
                lock.release();
            } catch (Exception e0) {
                // 处理释放异常
                LOGGER.error("zk释放锁异常 ", e0);
            }
        }
    }
}
