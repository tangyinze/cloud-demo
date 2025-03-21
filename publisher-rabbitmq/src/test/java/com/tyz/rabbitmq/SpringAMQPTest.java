package com.tyz.rabbitmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.nio.charset.StandardCharsets;

/**
 * SpringAMQP提供了三个功能：
 * 自动声明队列、交换机及其绑定关系
 * 基于注解的监听器模式，异步接收消息
 * 封装了RabbitTemplate工具，用于发送消息
 *
 * @program: cloud-demo
 * @description: 使用Spring AMQP 来发送消息
 * @author: tyz
 * @create: 2025-03-19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringAMQPTest {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Test
    public void testSendMessage2SimpleQueue() {
        // 队列名称
        String queueName = "tyz.Simple.queue";
        // 消息
        String message = "hello,tyz spring amqp!";
        // 发送消息
        rabbitTemplate.convertAndSend(queueName, message);
    }

    @Test
    public void testSendMessageQueue() {
        // 队列名称
        String queueName = "tyz.Simple.queue";
        // 消息 模拟发50条消息 到一个队列 让多2个消费者去消费
        String message = "spring amqp-id:";
        for (int ii = 0; ii < 50; ii++) {
            // 发送消息
            rabbitTemplate.convertAndSend(queueName, message + ii);
        }
        System.out.println("message send over....");
    }

    @Test
    public void testSendMessageFanoutExchange() {
        // 队列名称
        String exchangeName = "tyz.fanout.ex";
        // 消息 模拟发50条消息 到一个队列 让多2个消费者去消费
        String message = "exchange msg amqp-id:";
        for (int ii = 0; ii < 5; ii++) {
            // 发送消息
            rabbitTemplate.convertAndSend(exchangeName, "", message + ii);
        }
        System.out.println("message send over....");
    }

    @Test
    public void testSendMessageDirectExchange() {
        // 队列名称
        String exchangeName = "tyz.direct";
        // 消息 模拟发50条消息 到一个队列 让多2个消费者去消费
        String message = "hello,blue!";
        // 发送消息
        rabbitTemplate.convertAndSend(exchangeName, "blue", message);
        String message2 = "hello,red!";
        rabbitTemplate.convertAndSend(exchangeName, "red", message2);
        System.out.println("message send direct over....");
    }

    // 测试TopicExchange模式
    @Test
    public void testSendTopicExchange() {
        // 交换机名称
        String exchangeName = "tyz.topic";
        // 消息
        String message = "tyz.topic message！";
        // 发送消息，参数分别是：交互机名称、RoutingKey、消息
        rabbitTemplate.convertAndSend(exchangeName, "china.news", message);

        String message2 = "tyz.topic 2 message！";
        rabbitTemplate.convertAndSend(exchangeName, "uas.news", message2);
        System.out.println("message send topic over....");
    }

    /**
     * <p>
     *     测试发送死信息消息
     * </p>
     */
    @Test
    public void testSendDlxExchange() {
        // 交换机名称
        String exchangeName = "tyz.work.direct.dlx";
        // 消息
        // 发送消息，参数分别是：交互机名称、RoutingKey、消息
        for (int ii = 0; ii < 36; ii++) {
            String message = "tyz dlx message[ " + ii + " ]！";
            rabbitTemplate.convertAndSend(
                    exchangeName,
                    "s-dlx",
                    message.getBytes(StandardCharsets.UTF_8),
                    msg -> {
                        msg.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                        msg.getMessageProperties().setExpiration("1000");
                        return msg;
                    }
            );

        }
        System.out.println("message send topic dlx msg over....");


    }

}
