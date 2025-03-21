package com.tyz.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitmq 中的死信消息：
 *          1 队列没有消息者，且消息设置失效时间TTL 过期。到时就是死信息
 *          2 队列达到最大长度(队列满了，无法再添加数据到 mq 中)，最早消息就可能成为死信
 *          3 消费方消息被拒绝(basic.reject 或 basic.nack)并且消息requeue参数设置为false不重新入队列
 *
 *          下边来模拟第一种情况来实现消息延迟
 *          正常交换机->正常队列
 *          死信交换机->死信队列
 *          正常队列绑定死信息交换机
 *
 * @program: cloud-demo
 * @description: 死信息队列来模拟延迟处理消息
 * @author: tyz
 * @create: 2025-03-21
 */
@Configuration
public class DLXRabbitMqConfig {

    /**
     * <p>
     *     正常交换机 消息接收入口
     * </p>
     * @return exchange
     */
    @Bean
    public Exchange workDlxDirectExchange(){
        return ExchangeBuilder
                .directExchange("tyz.work.direct.dlx")
                .durable(true)
                .build();
    }

    /**
     * <p>
     *     正常交换机路由消息的队列
     *     绑定死信息交换机和路由信息
     * </p>
     * @return exchange
     */
    @Bean
    public Queue workDlxDirectQueue(){
        return QueueBuilder
                .durable("tyz.work.direct.queue.dlx")
                .lazy()
                // 绑定死信息交换机
                .deadLetterExchange("tyz.direct.dlx")
                .deadLetterRoutingKey("dlx")
                .ttl(60000)
                .maxLength(20L)
                .build();
    }

    /**
     * <p>
     *     正常交换机绑定消息的队列
     * </p>
     * @return binding
     */
    @Bean
    public Binding workDlxDirectBind(){
        return BindingBuilder
                .bind(workDlxDirectQueue())
                .to(workDlxDirectExchange())
                .with("s-dlx")
                .noargs();
    }


    /**
     * <p>
     *     死信息交换机 消息接收入口
     * </p>
     * @return exchange
     */
    @Bean
    public Exchange dlxDirectExchange(){
        return ExchangeBuilder
                .directExchange("tyz.direct.dlx")
                .durable(true)
                .build();
    }
    /**
     * <p>
     *     死信交换机路由消息的死信队列
     * </p>
     * @return exchange
     */
    @Bean
    public Queue dlxDirectQueue(){
        return QueueBuilder
                .durable("tyz.direct.queue.dlx")
                .lazy()
                .build();
    }

    /**
     * <p>
     *     死信交换机绑定消息的死信队列
     * </p>
     * @return binding
     */
    @Bean
    public Binding dlxDirectBind(){
        return BindingBuilder
                .bind(dlxDirectQueue())
                .to(dlxDirectExchange())
                .with("dlx")
                .noargs();
    }

}
