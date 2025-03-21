package com.tyz.rabbitmq.message;


import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

/**
 * @program: cloud-demo
 * @description: 生产者发topic 的消息
 * @author: tyz
 * @create: 2025-03-20
 */
@Component
public class ProductTopicMsg {
    public static final String TOPIC_EX_NAME = "spring_boot_topic_exchange";

    public final RabbitTemplate rabbitTemplate;

    @Autowired
    public ProductTopicMsg(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sentTaskMsg(String msg) {
        rabbitTemplate.convertAndSend(TOPIC_EX_NAME,
                "task",
                msg.getBytes(StandardCharsets.UTF_8),
                getMessagePersistent()
        );
    }

    public void sentLogMsg(String msg) {
        rabbitTemplate.convertAndSend(TOPIC_EX_NAME,
                "log",
                msg.getBytes(StandardCharsets.UTF_8),
                getMessagePersistent()
        );
    }
    public void sentJobMsg(String msg) {
        // 保证消息唯一性
        CorrelationData cd = new CorrelationData(UUID.randomUUID().toString());
        //

        rabbitTemplate.convertAndSend(
                TOPIC_EX_NAME,
                "job.",
                msg.getBytes(StandardCharsets.UTF_8),
                getMessagePersistent(),
                cd
        );
    }

    /**
     * <p>消息持久化</p>
     *
     * @return MessagePostProcessor
     */
    private static MessagePostProcessor getMessagePersistent() {
        return message -> {
            // 消息也持久化
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        };
    }
}
