package com.tyz.order.controller;

import com.tyz.order.bean.OrderVO;
import com.tyz.order.config.OrderProperties;
import com.tyz.order.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @program: cloud-demo
 * @description: OrderController 订单服务类
 * @author: tyz
 * @create: 2025-03-12
 */
@RefreshScope
@RestController
public class OrderController {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    @Value("${order.timeout:0s}")
    String timeOut;
    @Value("${order.auto-confirm:0d}")
    String autoConfirm;

    private OrderProperties orderProperties;
    IOrderService orderService;
    @Autowired
    public OrderController(OrderProperties orderProperties, IOrderService orderService) {
        this.orderProperties = orderProperties;
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

    /**
     * 获取nacos 配置中心的配置 数据集dataId
     * 如果使用@value 必须和类级别的@RefreshScope 才会自动生效不用重起
     *
     * @return map
     */
    @GetMapping("/order/nacos/cfg")
    public Map<String, String> loadOrderCfg() {
        Map<String, String> nacosOrderCfg = Map.of("timeOut", timeOut, "autoConfirm", autoConfirm);
        LOGGER.info("nacosOrderCfg:{}", nacosOrderCfg);
        return nacosOrderCfg;
    }

    @GetMapping("/order/nacos/cfg2")
    public Map<String, String> loadOrderCfg2() {
        Map<String, String> nacosOrderCfg = Map.of("timeOut", orderProperties.getTimeout(), "autoConfirm", orderProperties.getAutoConfirm());
        LOGGER.info("nacosOrderCfg:{}", nacosOrderCfg);
        return nacosOrderCfg;
    }

}
