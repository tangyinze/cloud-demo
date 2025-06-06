package com.tyz.rabbitmq.config.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: cloud-demo
 * @description: zk configs
 * @author: tyz
 * @create: 2025-04-23
 */

//@Configuration
public class ZkConfig {
    @Value("${zookeeper.connect-string}")
    private String connectString;

    @Value("${zookeeper.session-timeout}")
    private int sessionTimeout;

    @Value("${zookeeper.connection-timeout}")
    private int connectionTimeout;


    /**
     * <p>
     *     Curator zk 集成框架
     * </p>
     *
     * @return CuratorFramework
     */
    @Bean
    public CuratorFramework curatorFramework() {
        RetryPolicy retryPolicy = new RetryNTimes(3, 1000);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(connectString)
                .sessionTimeoutMs(sessionTimeout)
                .connectionTimeoutMs(connectionTimeout)
                .retryPolicy(retryPolicy)
                .build();
        client.start();
        return client;
    }
}
