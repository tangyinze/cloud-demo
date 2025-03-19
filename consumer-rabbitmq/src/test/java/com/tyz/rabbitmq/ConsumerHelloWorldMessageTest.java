package com.tyz.rabbitmq;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @program: cloud-demo
 * @description: 测试消费消息
 * @author: tyz
 * @create: 2025-03-19
 */

@SpringBootTest
public class ConsumerHelloWorldMessageTest {
    @Test
    public void consumerMessage() throws IOException, TimeoutException, InterruptedException {
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
        String queueName = "tyz.Simple.queue";
        channel.queueDeclare(queueName,
                false,
                false,
                false,
                null);

        // step 5: 订阅消息
        channel.basicConsume(
                queueName,
                true,
                new DefaultConsumer(channel) {
                    /**
                     * 处理已到达的消息
                     * @param consumerTag consumerTag
                     * @param envelope envelope
                     * @param properties 配置信息
                     * @param body 消息内容
                     * @throws IOException 异常
                     */
                    @Override
                    public void handleDelivery(String consumerTag,
                                               Envelope envelope,
                                               AMQP.BasicProperties properties,
                                               byte[] body) throws IOException {
                        // step 6 处理消息
                        String message = new String(body, StandardCharsets.UTF_8);
                        System.out.println("接收到消息：[" + message + "]");
                    }
                }
        );

        System.out.println("开始.....等待接收.....");

        TimeUnit.SECONDS.sleep(5);
        System.out.println("估计5秒.....处理完接收消息.....准备释放资源");
        // step 7 关闭通道和连接
        channel.close();
        connection.close();
    }
}
