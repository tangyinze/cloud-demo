package com.tyz.rabbitmq.common;

import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.tyz.rabbitmq.vo.OrderEvent;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * @program: cloud-demo
 * @description: OrderProducer
 * @author: tyz
 * @create: 2025-05-01
 */
@Component
public class OrderProducer {
    @Autowired
    @Qualifier("orderDisruptor")
    //@Lazy
    private Disruptor<OrderEvent> orderDisruptor;

    public void publishOrder(String orderId) {
        RingBuffer<OrderEvent> ringBuffer = orderDisruptor.getRingBuffer();
        long sequence = ringBuffer.next();
        try {
            // 填充数据
            ringBuffer.get(sequence).setOrderId(orderId);
        } finally {
            ringBuffer.publish(sequence);
        }
    }
}

