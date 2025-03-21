package com.tyz.rabbitmq.message;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTopicMsgTest {
    @Autowired
    public ProductTopicMsg productTopicMsg;
    @Test
    public void sentTaskMsg() {
        productTopicMsg.sentTaskMsg("spring topic task msg");
    }

    @Test
    public void sentLogMsg() {
        productTopicMsg.sentLogMsg("spring topic log msg");
    }

    @Test
    public void sentJobMsg() {
        productTopicMsg.sentJobMsg("spring topic job msg");
        try {
            TimeUnit.SECONDS.sleep(12);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}