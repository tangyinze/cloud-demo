package com.tyz.order.service.impl;

import com.tyz.order.bean.OrderVO;
import com.tyz.order.service.IOrderService;
import com.tyz.procuct.bean.ProductVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @program: cloud-demo
 * @description: OrderServiceImpl 订单服务类
 * @author: tyz
 * @create: 2025-03-12
 */
@Service
public class OrderServiceImpl implements IOrderService {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    // 服务发现 所有实例 需要自已实现负载均衡
    DiscoveryClient discoveryClient;

    // spring-cloud 自带的负载均衡 默认是轮询
    LoadBalancerClient loadBalancerClient;

    // 远成服务调用
    RestTemplate restTemplate;

    public OrderServiceImpl(DiscoveryClient discoveryClient, LoadBalancerClient loadBalancerClient, RestTemplate restTemplate) {
        this.discoveryClient = discoveryClient;
        this.loadBalancerClient = loadBalancerClient;
        this.restTemplate = restTemplate;
    }

    /**
     * 根据商品ID和用户Id 来创建订单
     *
     * @param productId 商品ID
     * @param userId    用户Id
     * @return OrderVO 订单信息
     */
    @Override
    public OrderVO crateOrder(Long productId, Long userId) {
        // 获取商品信息
        ProductVO remoteProduct = getLoadBalancedAnnotationProduct(productId);
        OrderVO vo = new OrderVO();
        vo.setAddress("tyz home");
        vo.setUserId(userId);
        vo.setNickName("T");
        vo.setProductList(Arrays.asList(remoteProduct));
        // 这要调用商品的微服后再计算价格
        BigDecimal totalAmount = remoteProduct.getPrice().multiply(new BigDecimal(remoteProduct.getNum()));
        vo.setTotalAmount(totalAmount);
        // 模拟订单Id 用多线程安全生成随机数
        int orderId = ThreadLocalRandom.current().nextInt(100000, 999999 + 1);
        vo.setId(Long.valueOf(Integer.valueOf(orderId).toString()));
        return vo;
    }

    /**
     * 通过商品ID来调用商品微服来获取商品信息 带负载均衡
     *
     * @param productId 商品Id
     * @return 返回商品信息
     */
    private ProductVO getLoadbalancerRemoteProduct(Long productId) {
        // 1 通过商品服务所有机器IP+port 在去调用
        ServiceInstance productServer = loadBalancerClient.choose("service-product");
        // 组合URL localhost:9001/product/2
        String url = "http://" + productServer.getHost() + ":" + productServer.getPort() + "/product/" + productId;
        LOGGER.info("remote url: {}", url);
        // 2 给远成服务服务发送请求
        ProductVO proMsg = restTemplate.getForObject(url, ProductVO.class);
        LOGGER.info("remote proMsg: {}", proMsg);
        return proMsg;
    }

    /**
     * 通过商品ID来调用商品微服来获取商品信息
     *
     * @param productId 商品Id
     * @return 返回商品信息
     */
    private ProductVO getRemoteProduct(Long productId) {
        // 1 通过商品服务所有机器IP+port 在去调用
        List<ServiceInstance> instances = discoveryClient.getInstances("service-product");
        ServiceInstance productServer = instances.get(0);
        // 组合URL localhost:9001/product/2
        String url = "http://" + productServer.getHost() + ":" + productServer.getPort() + "/product/" + productId;
        LOGGER.info("remote url: {}", url);
        // 2 给远成服务服务发送请求
        ProductVO proMsg = restTemplate.getForObject(url, ProductVO.class);
        LOGGER.info("remote proMsg: {}", proMsg);
        return proMsg;
    }
    /**
     * 通过商品ID来调用商品微服来获取商品信息
     *  restTemplate + @LoadBalanced 就可以使的restTemplate 自带负载均衡
     *
     * @param productId 商品Id
     * @return 返回商品信息
     */
    private ProductVO getLoadBalancedAnnotationProduct(Long productId) {
        // 1 通过商品服务http://+微服名+/product/productId
        String url = "http://service-product/product/" + productId;
        LOGGER.info("remote url: {}", url);
        // 2 给远成服务服务发送请求
        ProductVO proMsg = restTemplate.getForObject(url, ProductVO.class);
        LOGGER.info("remote proMsg: {}", proMsg);
        return proMsg;
    }
}
