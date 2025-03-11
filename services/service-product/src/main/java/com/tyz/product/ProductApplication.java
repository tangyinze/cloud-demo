package com.tyz.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @program: cloud-demo
 * @description: product service test
 * @author: tyz
 * @create: 2025-03-07
 */
// 开启服务发现的注解
@EnableDiscoveryClient
// Springboot 启动类的注解
@SpringBootApplication
public class ProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductApplication.class, args);
    }
}
