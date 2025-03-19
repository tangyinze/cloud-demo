package com.tyz.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @program: cloud-demo
 * @description: 测试发消息
 * @author: tyz
 * @create: 2025-03-19
 */
@SpringBootTest
public class HelloWorldMassageTest {
    @Test
    public void testSendMessage() throws IOException, TimeoutException {
        // step 1: 建立mq的连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // step 1.1 :绑定rabbitmq的参数 主机名（ip）| vhost|用户名|密码
        factory.setHost("172.16.248.130");
        factory.setPort(5672);
        factory.setVirtualHost("dev");
        factory.setUsername("rabbit");
        factory.setPassword("rabbit");
        // step 2: 从工厂获取mq的连接
        Connection connection = factory.newConnection();

        // step 3: 从连接里获取channel
        Channel channel = connection.createChannel();

        // step 4: 创建对列
        String queueName = "tyz.Simple.queue0";
        channel.queueDeclare(queueName,
                false,
                false,
                false,
                null);

        // step 5 发送消息
        String message = "hello,rabbitmq!";
        channel.basicPublish(
                // 交换器
                "",
                // 队列名
                queueName,
                // 消息的配置信息
                null,
                // 消息的内容字节数组
                message.getBytes(StandardCharsets.UTF_8)
        );
        System.out.println("发送消息成功");

        // step 6 关闭通道和连接
        channel.close();
        connection.close();

    }
}
