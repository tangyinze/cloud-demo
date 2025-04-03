package com.tyz.rabbitmq.common;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RedisUtilTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtilTest.class);
    @Autowired
    private RedisUtil redisUtil;
    @Test
    void expire() {
        redisUtil.setKeyVal("tt", "expire");
        Boolean tt = redisUtil.expire("tt", 2);
        LOGGER.info("tt expire action:{}", tt);
    }

    @Test
    void getExpire() {
        long tt = redisUtil.getExpire("tt");
        LOGGER.info("tt ttl:{}", tt);
    }
}