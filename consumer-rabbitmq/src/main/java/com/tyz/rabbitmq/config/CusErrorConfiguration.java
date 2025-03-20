package com.tyz.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: cloud-demo
 * @description: 消息失败的策略 绑定一个异常交换机肯异常队列
 * @author: tyz
 * @create: 2025-03-21
 */
@ConditionalOnProperty(
        prefix = "spring.rabbitmq.listener.simple.retry",
        name = "enabled",
        havingValue = "true"
)
@Configuration
public class CusErrorConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CusErrorConfiguration.class);

    @Bean
    public Exchange cusErrorExchange() {
        return ExchangeBuilder
                .directExchange("error.cus.direct")
                .durable(true)
                .build();

    }

    @Bean
    public Queue cusErrorQueue() {
        return QueueBuilder
                .durable("error.cus.queue")
                .lazy()
                .maxLength(3L)
                .overflow(QueueBuilder.Overflow.dropHead)
                .build();
    }

    @Bean
    public Binding cusErrorBinding() {
        return BindingBuilder
                .bind(cusErrorQueue())
                .to(cusErrorExchange())
                .with("error")
                .noargs();
    }

    /**
     * //失败后将消息投递到一个指定的，专门存放异常消息的队列，后续由人工集中处理。
     * @param rabbitTemplate rabbitTemplate
     * @return obj
     */
    @Bean
    public MessageRecoverer cusErrorMessageRecoverer(RabbitTemplate rabbitTemplate) {
        LOGGER.info("cusError MessageRecoverer come");
        return new RepublishMessageRecoverer(
                rabbitTemplate,
                "error.cus.direct",
                "error");
    }
}
