package com.tyz.order.controller;

import com.tyz.order.bean.OrderVO;
import com.tyz.order.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: cloud-demo
 * @description: OrderController 订单服务类
 * @author: tyz
 * @create: 2025-03-12
 */
@RestController
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    IOrderService orderService;
    @Autowired
    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }

    /**
     *  根据 商品ID和用户ID 来创建订单信息
     * @param productId 商品ID
     * @param userId 用户ID
     * @return OrderVO 订单信息
     */
    @GetMapping("/order/crate")
    public OrderVO crateOrder(@RequestParam("productId") Long productId,
                              @RequestParam("userId") Long userId) {
        OrderVO vo = orderService.crateOrder(productId, userId);
        LOGGER.info("order:{}", vo);
        return vo;
    }

}
