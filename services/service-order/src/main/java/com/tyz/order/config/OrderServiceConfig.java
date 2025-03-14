package com.tyz.order.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @program: cloud-demo
 * @description: OrderService 的一些配置 例如 restTemplate
 * @author: tyz
 * @create: 2025-03-14
 */
@Configuration
public class OrderServiceConfig {
    // 注解版的负载均衡
    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
