package com.tyz.rabbitmq.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * @program: cloud-demo
 * @description: 生产者 RabbiteTemplage 只能配置一个ReturnCallbak 在项目启动过程配置
 * @author: tyz
 * @create: 2025-03-20
 */
@Configuration
public class RabbitComSentReturnCallBackConfig implements ApplicationContextAware {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitComSentReturnCallBackConfig.class);
    /**
     * @param applicationContext 容器上下文
     * @throws BeansException 异常信息
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RabbitTemplate rabbitTemplate = applicationContext.getBean(RabbitTemplate.class);
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            /**
             * <p>
             *     统一 消息是否到达队列的返回 处理
             * </p>
             * @param message msg
             */
            @Override
            public void returnedMessage(ReturnedMessage message) {
                LOGGER.info(
                        "消息发送 应答码:{},原因:{},交换机:{},路由:{},消息:{}",
                        message.getReplyCode(),
                        message.getReplyText(),
                        message.getExchange(),
                        message.getRoutingKey(),
                        message.getMessage()
                );
            }
        });

        // 重写发送者 确认的回调（Springboot 3 不在支持在发送时差异化的确认了，2是可以的）
        // Only one ConfirmCallback is supported by each RabbitTemplate
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback(){
            /**
             * @param correlationData  数据内容
             * @param ack 确认码
             * @param cause 原因
             */
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                LOGGER.info(
                        "confirm 消息发送应答码:{},原因:{},消息:{}",
                        ack,
                        cause,
                        correlationData
                );

                if (!ack) {
                    LOGGER.info("exchange produce confirm message send error:{}", cause);
                } else {
                    LOGGER.info("exchange produce confirm message send success");
                }
            }
        });

    }
}
