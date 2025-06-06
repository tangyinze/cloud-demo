package com.tyz.rabbitmq.config.disruptor;

import com.lmax.disruptor.ExceptionHandler;
import com.lmax.disruptor.dsl.Disruptor;
import com.tyz.rabbitmq.vo.LogVO;
import com.tyz.rabbitmq.vo.OrderEvent;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.logging.log4j.core.LogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @program: cloud-demo
 * @description: DisruptorStarter
 * @author: tyz
 * @create: 2025-06-05
 */
@Component
public class DisruptorStarter {
    private static final Logger LOGGER = LoggerFactory.getLogger(DisruptorStarter.class);
    @Autowired
    @Qualifier("orderDisruptor")
    private Disruptor<OrderEvent> orderDisruptor;

    @Autowired
    @Qualifier("logDisruptor")
    private Disruptor<LogVO> logDisruptor;

    @PostConstruct
    public void init() {
        /**
         * <p>
         *     一对多广播‌:单个生产者向多个消费者广播事件，每个消费者处理全量数据。
         *              disruptor.handleEventsWith(handlerA)
         *                                  .and(handlerB)
         *                                  .and(handlerC);
         *    顺序依赖处理（Diamond Pattern）
         *    分阶段处理‌：事件需按顺序通过多个处理阶段（如数据校验 → 数据加工 → 数据存储）。
         *             disruptor.handleEventsWith(new ValidationHandler())
         *                                .then(new DataProcessorHandler())
         *                                .then(new StorageHandler());

         *    并行消费者（Parallel Consumers）
         *    独立处理‌：多个消费者同时处理不同事件，提升吞吐量
         *          EventHandler<LogEvent> handler1 = new LogEventHandler();
         *          EventHandler<LogEvent> handler2 = new LogEventHandler();
         *                   disruptor.handleEventsWith(handler1, handler2);
         * </p>
         */
        // 订单处理器链
        orderDisruptor.handleEventsWith((event, sequence, endOfBatch) -> {
            // 处理订单逻辑
            LOGGER.info("{} OrderEvent msg {}", Thread.currentThread().getName(), event);
        });
        orderDisruptor.setDefaultExceptionHandler(new ExceptionHandler<>() {
            @Override
            public void handleEventException(Throwable ex, long sequence, OrderEvent orderEvent) {
                // 记录异常并继续处理后续事件
                LOGGER.error("处理事件失败: sequence={}, event={}", sequence, orderEvent, ex);
            }

            @Override
            public void handleOnStartException(Throwable throwable) {
                LOGGER.error(" OrderEvent handleOnStartException", throwable);
            }

            @Override
            public void handleOnShutdownException(Throwable throwable) {
                LOGGER.error(" OrderEvent handleOnShutdownException", throwable);
            }
        });
        orderDisruptor.start();  // 启动 RingBuffer:ml-citation{ref="8" data="citationList"}

        // 日志处理器链

        logDisruptor.handleEventsWith((event, sequence, endOfBatch) -> {
                    // 处理日志逻辑
                    LOGGER.info("{} logVo one msg {}", Thread.currentThread().getName(), event);
                },
                (event, sequence, endOfBatch) -> {
                    // 处理日志逻辑
                    LOGGER.info("{} logVo two msg {}", Thread.currentThread().getName(), event);
                });
        logDisruptor.setDefaultExceptionHandler(new ExceptionHandler<>() {
            /**
             * @param ex  异常
             * @param sequence 位置
             * @param logVO 信息
             */
            @Override
            public void handleEventException(Throwable ex, long sequence, LogVO logVO) {
                // 记录异常并继续处理后续事件
                LOGGER.error("处理事件失败: sequence={}, event={}", sequence, logVO, ex);
            }

            /**
             * @param throwable 导常
             */
            @Override
            public void handleOnStartException(Throwable throwable) {
                LOGGER.error(" logVO handleOnStartException", throwable);
            }

            /**
             * @param throwable 导常
             */
            @Override
            public void handleOnShutdownException(Throwable throwable) {
                LOGGER.error(" logVO handleOnShutdownException", throwable);
            }
        });
        logDisruptor.start();
    }

    @PreDestroy
    public void shutdown() {
        orderDisruptor.shutdown();
        logDisruptor.shutdown();
    }
}
