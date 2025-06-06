package com.tyz.rabbitmq.common;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class DistributedLockZKServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DistributedLockZKServiceTest.class);
    @Autowired
    private DistributedLockZKService distributedLockZKService;

    @Test
    void executeWithLockTest() {
        Function<Object,String> stringFunction = (Object s) -> {
            if (s instanceof String) {
                return s.toString();
            } else if (s instanceof Integer) {
                return Integer.toString((Integer) s);
            } else {
                return "ok";
            }
        };
        for (int ii = 0; ii < 20; ii++) {
            int kk =ii;
            CompletableFuture.runAsync(() -> {
                for (int jj = 0; jj < 10; jj++) {
                    distributedLockZKService.executeWithLock(
                            "prd" + kk,
                            Integer.valueOf(kk),
                            stringFunction
                    );
                    LOGGER.info("executeWithLock 【{}-{}】 ThreadName:{}-{}", kk, jj, Thread.currentThread().getName(), jj);
                }
            });
        }

    }
}