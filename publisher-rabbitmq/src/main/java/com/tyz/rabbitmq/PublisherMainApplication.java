package com.tyz.rabbitmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @program: cloud-demo
 * @description: rabbitmq 消息发送方服务
 * @author: tyz
 * @create: 2025-03-19
 */
@SpringBootApplication
public class PublisherMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(PublisherMainApplication.class, args);
    }
}
