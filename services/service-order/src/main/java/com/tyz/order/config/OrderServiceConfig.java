package com.tyz.order.config;

import feign.Logger;
import feign.Retryer;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

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

    /**
     * 开启feign的日志
     *
     * @return level
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * <p>Feign 默认是关闭重试的，这里是用来测试用</p>
     *
     * @return  重试策略
     */
    @Bean
    Retryer retryer(){
        return new Retryer.Default(100L, TimeUnit.SECONDS.toMillis(1L), 3);
    }
}
