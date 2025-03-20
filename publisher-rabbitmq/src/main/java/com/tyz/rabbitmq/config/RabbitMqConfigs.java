package com.tyz.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: cloud-demo
 * @description: 生产者rabbit的配置
 * @author: tyz
 * @create: 2025-03-19
 */
@Configuration
public class RabbitMqConfigs {
    public static final String TOPIC_EX_NAME = "spring_boot_topic_exchange";

    public static final String TOPIC_QUE_MAIN = "spring_boot_topic_queue_main";

    public static final String TOPIC_QUE_SECOND = "spring_boot_topic_queue_second";

    public static final String TOPIC_QUE_THIRD = "spring_boot_topic_queue_third";

    /**
     * topic TOPIC_EX_NAME
     *       绑定三个队列
     *       TOPIC_QUE_MAIN
     *       TOPIC_QUE_SECOND
     *       TOPIC_QUE_THIRD
     *
     * @return Exchange
     */
    @Bean("spTopicExChange")
    public Exchange spTopicExChange() {
        return ExchangeBuilder.
                topicExchange(TOPIC_EX_NAME)
                .durable(true)
                .build();
    }

    /**
     * <p>
     * 队列一：TOPIC_QUE_MAIN
     * 开启lazy 模型同时也持久化了
     * </p>
     *
     * @return queue
     */
    @Bean("spTopicQueueMain")
    public Queue spTopicQueueMain() {
        return QueueBuilder
                .durable(TOPIC_QUE_MAIN)
                .lazy()
                .build();
    }

    /**
     * <p>
     * 队列二：TOPIC_QUE_SECOND
     * </p>
     *
     * @return queue
     */
    @Bean("spTopicQueueSecond")
    public Queue spTopicQueueSecond() {
        return QueueBuilder
                .durable(TOPIC_QUE_SECOND)
                .lazy()
                .build();
    }

    /**
     * <p>
     * 队列三：TOPIC_QUE_THIRD
     * </p>
     *
     * @return queue
     */
    @Bean("spTopicQueueThird")
    public Queue spTopicQueueThird() {
        return QueueBuilder
                .durable(TOPIC_QUE_THIRD)
                .lazy()
                .build();
    }

    /**
     * <p>
     *     队列绑定交换机
     * </p>
     * @param queue 队列
     * @param exchange 交换机
     * @return binding
     */
    @Bean("bingSpTopicQueMainExchange")
    public Binding bingSpTopicQueMainExchange(@Qualifier("spTopicQueueMain") Queue queue, @Qualifier("spTopicExChange") Exchange exchange) {
        return BindingBuilder.
                bind(queue).
                to(exchange).
                with("log.#")
                .noargs();
    }

    /**
     * <p>
     *     绑定第二个队列
     * </p>
     * @return obj
     */
    @Bean("bingSpTopicQueSecondExchange")
    public Binding bingSpTopicQueSecondExchange() {
        return BindingBuilder.
                bind(spTopicQueueSecond()).
                to(spTopicExChange()).
                with("#.task")
                .noargs();
    }
    /**
     * <p>
     *     绑定第三个队列
     * </p>
     * @return obj
     */
    @Bean("bingSpTopicQueThirdExchange")
    public Binding bingSpTopicQueThirdExchange() {
        return BindingBuilder.
                bind(spTopicQueueThird()).
                to(spTopicExChange()).
                with("job.*")
                .noargs();
    }

}
