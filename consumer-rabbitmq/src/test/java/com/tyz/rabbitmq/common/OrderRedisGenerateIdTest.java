package com.tyz.rabbitmq.common;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;

@SpringBootTest
public class OrderRedisGenerateIdTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRedisGenerateIdTest.class);
    @Autowired
    private OrderRedisGenerateId orderRedisGenerateId;
    @Test
    void generateOrderIdTest() {
        // ForkJoinPool.commonPool 以下代码同一时间有14的并发量 拿到同一时间撮
        for (int ii = 0; ii < 50; ii++) {
            int kk =ii;
            CompletableFuture.runAsync(() -> {
                for (int jj = 0; jj < 20; jj++) {
                    String id = orderRedisGenerateId.generateOrderId();
                    LOGGER.info("generateOrderId 【{}-{}】 ThreadName:{}-{}", kk, jj, Thread.currentThread().getName(), id);
                }
            });
        }
    }
}