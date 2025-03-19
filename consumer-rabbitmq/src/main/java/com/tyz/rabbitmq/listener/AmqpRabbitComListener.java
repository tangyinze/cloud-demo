package com.tyz.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @program: cloud-demo
 * @description: AQMP 监听消息
 * @author: tyz
 * @create: 2025-03-19
 */
@Component
public class AmqpRabbitComListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpRabbitComListener.class);

    @RabbitListener(
            // 注意这样声明队列 如果不存的 启动会报错的，正确做法可以采用声明式bean配置注解 或者 基于注解声明队列和交换机
            queues = "tyz.Simple.queue0"
    )
    public void listenSimpleQueue0(String message) {
        LOGGER.info("消费者0接收到 tyz.Simple.queue0的消息：{}", message);
    }

    @RabbitListener(queues = "tyz.Simple.queue")
    public void listenSimpleQueue(String message) {
        LOGGER.info("消费者1接收到 tyz.Simple.queue的消息：{}", message);
        try {
            // 消息消费的耗时模拟
            TimeUnit.MILLISECONDS.sleep(20);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "tyz.Simple.queue")
    public void listenSimpleQueue2(String message) {
        LOGGER.info("消费者2接收到 tyz.Simple.queue的消息：{}", message);
        try {
            // 消息消费的耗时模拟
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            // 消除当前线程的中断状态
            Thread.currentThread().interrupt();
            // 处理中断后的逻辑
            throw new RuntimeException(e);
        }
    }

    @RabbitListener(queues = "fanout.queue.main")
    public void listenFanoutQueueMain(String message) {
        LOGGER.info("消费者main接收到 fanout.queue.main的消息：{}", message);
    }

    @RabbitListener(queues = "fanout.queue.second")
    public void listenFanoutQueueSecond(String message) {
        LOGGER.info("消费者second接收到 fanout.queue.second的消息：{}", message);
    }

    // 基于注解声明队列和交换机

    @RabbitListener(
            bindings =
            @QueueBinding(
                    // 绑定队列信息
                    value = @Queue("tyz.direct.queue.main"),
                    // 绑定交换机信息
                    exchange = @Exchange(
                            name = "tyz.direct",
                            type = ExchangeTypes.DIRECT
                    ),
                    // 消息路由键
                    key = {"red", "yellow"}
            )
    )
    public void listenerDirectQueueMain(String message) {
        LOGGER.info("消费者main接收到交换机：tyz.direct的队列 tyz.direct.queue.main的消息：{}", message);
    }

    @RabbitListener(
            bindings =
            @QueueBinding(
                    // 绑定队列信息
                    value = @Queue("tyz.direct.queue.second"),
                    // 绑定交换机信息
                    exchange = @Exchange(
                            name = "tyz.direct",
                            type = ExchangeTypes.DIRECT
                    ),
                    // 消息路由键
                    key = {"red", "blue"}
            )
    )
    public void listenerDirectQueueSecond(String message) {
        LOGGER.info("消费者second接收到交换机：tyz.direct的队列 tyz.direct.queue.second的消息：{}", message);
    }

    /**
     * <p> 注解申明topic申明闻交互机和队列</p>
     *
     * @param message 消息
     */
    @RabbitListener(
            bindings =
            @QueueBinding(
                    // 绑定队列信息
                    value = @Queue("tyz.topic.queue.main"),
                    // 绑定交换机信息
                    exchange = @Exchange(
                            name = "tyz.topic",
                            type = ExchangeTypes.TOPIC
                    ),
                    // 消息路由键
                    key = "#.news"
            )
    )
    public void listenerTopicQueueMain(String message) {
        LOGGER.info("消费者 topic main接收到交换机：tyz.topic的队列 yz.topic.queue.main的消息：{}", message);
    }

    @RabbitListener(
            bindings =
            @QueueBinding(
                    // 绑定队列信息
                    value = @Queue("tyz.topic.queue.second"),
                    // 绑定交换机信息
                    exchange = @Exchange(
                            name = "tyz.topic",
                            type = ExchangeTypes.TOPIC
                    ),
                    // 消息路由键
                    key = "china.#"
            )
    )
    public void listenerTopicQueueSecond(String message) {
        LOGGER.info("消费者 topic second接收到交换机：tyz.topic的队列 tyz.topic.queue.second的消息：{}", message);
    }
}
