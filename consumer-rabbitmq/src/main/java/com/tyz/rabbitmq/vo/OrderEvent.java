package com.tyz.rabbitmq.vo;

/**
 * @program: cloud-demo
 * @description: OrderEvent msg 订单事件
 * @author: tyz
 * @create: 2025-05-01
 */
public class OrderEvent {
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderEvent{" +
                "orderId='" + orderId + '\'' +
                '}';
    }
}
