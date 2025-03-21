package com.tyz.rabbitmq.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
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
    public static final String TOPIC_EX_NAME = "spring_boot_topic_exchange";

    public static final String TOPIC_QUE_MAIN = "spring_boot_topic_queue_main";

    public static final String TOPIC_QUE_SECOND = "spring_boot_topic_queue_second";

    public static final String TOPIC_QUE_THIRD = "spring_boot_topic_queue_third";

    /*@RabbitListener(
            // 注意这样声明队列 如果不存的 启动会报错的，正确做法可以采用声明式bean配置注解 或者 基于注解声明队列和交换机
            queues = "tyz.Simple.queue0"
    )
    public void listenSimpleQueue0(String message) {
        LOGGER.info("消费者0接收到 tyz.Simple.queue0的消息：{}", message);
    }*/

    //@RabbitListener(queues = "tyz.Simple.queue")
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

    //@RabbitListener(queues = "tyz.Simple.queue")
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

    @RabbitListener(
            bindings =
            @QueueBinding(
                    // 绑定队列信息
                    value = @Queue(
                            name = TOPIC_QUE_MAIN,
                            durable = "true",
                            arguments = @Argument(
                                    // x-queue-mode 非 x-queue-leader-locator
                                    name = "x-queue-mode",
                                    value = "lazy"
                            )
                    ),
                    // 绑定交换机信息
                    exchange = @Exchange(
                            name = TOPIC_EX_NAME,
                            type = ExchangeTypes.TOPIC
                    ),
                    // 消息路由键
                    key = "log.#"
            )
    )
    public void listenerTopicSpQueueM(String message) {
        LOGGER.info(
                "消费者 main 接收到交换机:{}的lazy队列{}的消息：{}",
                TOPIC_EX_NAME,
                TOPIC_QUE_MAIN,
                message);
    }

    @RabbitListener(
            bindings =
            @QueueBinding(
                    // 绑定队列信息
                    value = @Queue(
                            name = TOPIC_QUE_SECOND,
                            durable = "true",
                            arguments = @Argument(
                                    name = "x-queue-mode",
                                    value = "lazy"
                            )
                    ),
                    // 绑定交换机信息
                    exchange = @Exchange(
                            name = TOPIC_EX_NAME,
                            type = ExchangeTypes.TOPIC
                    ),
                    // 消息路由键
                    key = "#.task"
            )
    )
    public void listenerTopicSpQueueS(String message) {
        LOGGER.info(
                "消费者 main 接收到交换机:{}的lazy队列{}的消息：{}",
                TOPIC_EX_NAME,
                TOPIC_QUE_SECOND,
                message);
    }

    @RabbitListener(
            bindings =
            @QueueBinding(
                    // 绑定队列信息
                    value = @Queue(
                            name = TOPIC_QUE_THIRD,
                            durable = "true",
                            arguments = @Argument(
                                    name = "x-queue-mode",
                                    value = "lazy"
                            )
                    ),
                    // 绑定交换机信息
                    exchange = @Exchange(
                            name = TOPIC_EX_NAME,
                            type = ExchangeTypes.TOPIC
                    ),
                    // 消息路由键
                    key = "job.*"
            )
    )
    @RabbitHandler
    public void listenerTopicSpQueueT(String msg) {
        LOGGER.info(
                "消费者main接收到交换机:{}的lazy队列{}的消息：{}",
                TOPIC_EX_NAME,
                TOPIC_QUE_THIRD,
                msg);
        LOGGER.info("开始消息确认auto重试机制加异常策略 不会无限重试");
        int c = 1 / 0;
    }
    /*public void listenerTopicSpQueueT(String msg, Channel channel, Message message) throws IOException {
        LOGGER.info(
                "消费者main接收到交换机:{}的lazy队列{}的消息：{}",
                TOPIC_EX_NAME,
                TOPIC_QUE_THIRD,
                msg);

        try {
            LOGGER.info("开始消息确认");
            int c = 1 / 0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            LOGGER.info("消息确认成功");
        } catch (Exception e) {
            LOGGER.error("消息确认失败，即将再次返回队列中");
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), true, true);
        }
    }*/

    @RabbitListener(
            bindings =
                    @QueueBinding(
                            value = @Queue(
                                    name = "tyz.direct.queue.dlx",
                                    durable = "true",
                                    arguments = @Argument(
                                            name = "x-queue-mode",
                                            value = "lazy"
                                    )
                            ),
                            exchange = @Exchange(
                                    name = "tyz.direct.dlx",
                                    durable = "true",
                                    type = ExchangeTypes.DIRECT
                            ),
                            key = "dlx"
                    )
    )
    public void dlxMsgHandler(String msg) {
        LOGGER.info(
                "消费者死信队列实现延时的消息消费：{}",
                msg);
    }
}
