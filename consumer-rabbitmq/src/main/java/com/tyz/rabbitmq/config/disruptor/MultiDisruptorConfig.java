package com.tyz.rabbitmq.config.disruptor;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.tyz.rabbitmq.vo.LogVO;
import com.tyz.rabbitmq.vo.OrderEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;

/**
 * @program: cloud-demo
 * @description: MultiDisruptorConfig
 * @author: tyz
 * @create: 2025-05-01
 */
@Configuration
public class MultiDisruptorConfig {
    // private static final Logger LOGGER = LoggerFactory.getLogger(MultiDisruptorConfig.class);
    @Bean("logDisruptor")
    public Disruptor<LogVO> logDisruptor() {
        EventFactory<LogVO> factory = ()-> { return new LogVO();};
        // 需为2的幂次方
        return new Disruptor<>(
                factory,
                // 需为2的幂次方
                1024,
                Executors.defaultThreadFactory(),
                ProducerType.SINGLE,
                // 线程策略‌：建议使用 BlockingWaitStrategy 平衡性能和资源
                new BlockingWaitStrategy()
        );
    }

    /**
     * <p>
     *     日志处理链
     * </p>
     * @return eventHandler
     */
   /* @Bean
    public EventHandler<LogVO> logEventHandler() {
        return (event, sequence, endOfBatch) -> {
            // 处理日志逻辑
            LOGGER.info("{} logVo one msg {}", Thread.currentThread().getName(), event);
        };
    }*/

   /* @Bean
    public EventHandler<LogVO> logEvent2Handler() {
        return (event, sequence, endOfBatch) -> {
            // 处理日志逻辑
            LOGGER.info("{} logVo two msg {}", Thread.currentThread().getName(), event);
        };
    }*/

    // 订单Disruptor
    @Bean("orderDisruptor")
    public Disruptor<OrderEvent> orderDisruptor() {
        return new Disruptor<>(
                OrderEvent::new,
                1024,
                Executors.newCachedThreadPool(),
                // 多生产者模式
                ProducerType.MULTI,
                new BlockingWaitStrategy()
        );
    }

    // 订单处理链
   /* @Bean
    public EventHandler<OrderEvent> orderEventHandler() {
        return (event, sequence, endOfBatch) -> {
            // 处理订单逻辑
            LOGGER.info("{} OrderEvent msg {}", Thread.currentThread().getName(), event);
        };
    }*/

    /**
     * <p>
     *     初始化绑定队列处理器并起动
     * </p>
     */
    /*@PostConstruct
    public void initMultiDisruptor() {
        // 订单队列绑定处理事件
        orderDisruptor().handleEventsWith(orderEventHandler());
        ***
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
         *//*
        // 日志信息队列绑定处理事件 并行消费者（Parallel Consumers）log和log2
        logDisruptor().handleEventsWith(logEventHandler(),logEvent2Handler());
        logDisruptor().setDefaultExceptionHandler(new ExceptionHandler<>() {
            **
             * @param ex  异常
             * @param sequence 位置
             * @param logVO 信息
             *//*
            @Override
            public void handleEventException(Throwable ex, long sequence, LogVO logVO) {
                // 记录异常并继续处理后续事件
                LOGGER.error("处理事件失败: sequence={}, event={}", sequence, logVO, ex);
            }

            ***
             * @param throwable 导常
             *
            @Override
            public void handleOnStartException(Throwable throwable) {
                LOGGER.error(" logVO handleOnStartException", throwable);
            }

            ***
             * @param throwable 导常
             *
            @Override
            public void handleOnShutdownException(Throwable throwable) {
                LOGGER.error(" logVO handleOnShutdownException", throwable);
            }
        });

        orderDisruptor().setDefaultExceptionHandler(new ExceptionHandler<OrderEvent>() {
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
        orderDisruptor().start();
        logDisruptor().start();
    }*/
}
