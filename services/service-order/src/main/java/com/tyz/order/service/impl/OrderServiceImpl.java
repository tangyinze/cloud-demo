package com.tyz.order.service.impl;

import com.tyz.order.bean.OrderVO;
import com.tyz.order.service.IOrderService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @program: cloud-demo
 * @description: OrderServiceImpl 订单服务类
 * @author: tyz
 * @create: 2025-03-12
 */
@Service
public class OrderServiceImpl implements IOrderService {
    /**
     * 根据商品ID和用户Id 来创建订单
     *
     * @param productId 商品ID
     * @param userId    用户Id
     * @return OrderVO 订单信息
     */
    @Override
    public OrderVO crateOrder(Long productId, Long userId) {
        OrderVO vo = new OrderVO();
        vo.setAddress("tyz home");
        vo.setUserId(userId);
        vo.setNickName("T");
        vo.setProductList(null);
        // TODO 这要调用商品的微服后再计算价格
        vo.setTotalAmount(new BigDecimal("12.0"));
        // 模拟订单Id 用多线程安全生成随机数
        int orderId = ThreadLocalRandom.current().nextInt(100000, 999999 + 1);
        vo.setId(Long.valueOf(Integer.valueOf(orderId).toString()));
        return vo;
    }
}
