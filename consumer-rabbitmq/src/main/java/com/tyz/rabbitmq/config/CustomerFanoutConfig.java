package com.tyz.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *     基于bean注解来绑定交换机和队列的关系
 * </p>
 *
 * @program: cloud-demo
 * @description: bean 注解来表明 交换机和队列的关系
 * @author: tyz
 * @create: 2025-03-19
 */
@Configuration
public class CustomerFanoutConfig {
    /**
     * <p>
     *     声明 tyz.fanout.ex 的交换机
     *     绑定 fanout.queue.main 的主队列
     *          fanout.queue.second 次队列
     * </p>
     *
     * @return FanoutExchange
     */
    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange("tyz.fanout.ex");
    }

    /**
     * <p>
     *     声明 fanout.queue.main 的主队列
     * </p>
     *
     * @return FanoutExchange
     */
    @Bean
    public Queue fanoutQueueMain(){
        return new Queue("fanout.queue.main");
    }

    /**
     * <p>
     *     fanout.queue.main 绑定交换机 tyz.fanout.ex
     * </p>
     *
     * @return Binding
     */
    @Bean
    public Binding bindingQueueMain(){
        return BindingBuilder
                .bind(fanoutQueueMain())
                .to(fanoutExchange());
    }

    /**
     * <p>
     *     声明 fanout.queue.second 的主队列
     * </p>
     *
     * @return FanoutExchange
     */
    @Bean
    public Queue fanoutQueueSecond(){
        return new Queue("fanout.queue.second");
    }

    /**
     * <p>
     *     fanout.queue.second 绑定交换机 tyz.fanout.ex
     * </p>
     *
     * @return Binding
     */
    @Bean
    public Binding bindingQueueSecond(){
        return BindingBuilder
                .bind(fanoutQueueSecond())
                .to(fanoutExchange());
    }
}
