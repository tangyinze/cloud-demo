package com.tyz.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: cloud-demo
 * @description: 网关服务
 * @author: tyz
 * @create: 2025-03-16
 */
// 开启服务发现
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayMainApplication {
    public static void main(String[] args) {
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(GatewayMainApplication.class, args);
    }
}
