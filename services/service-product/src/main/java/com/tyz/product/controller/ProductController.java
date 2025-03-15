package com.tyz.product.controller;

import com.tyz.procuct.bean.ProductVO;
import com.tyz.product.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;


/**
 * @program: cloud-demo
 * @description: product controller
 * @author: tyz
 * @create: 2025-03-12
 */
@RestController
public class ProductController {
    /***
     * 日志输出
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);
    /**
     * 商品服务类
     */
    public final IProductService productService;

    /**
     *  采用构造器注入
     * @param productService 商品服务类
     */
    @Autowired
    public ProductController(IProductService productService) {
        this.productService = productService;
    }

    /**
     * 根据商品ID查询商品的信息
     *
     * @param productId 商品ID
     * @return productVO 商品的信息
     */
    @GetMapping("/product/{id}")
    public ProductVO getProduct(@PathVariable("id") Long productId) {
        ProductVO proVo = productService.getProductById(productId);
        LOGGER.info("product info:{}", proVo);
        try {
            // TODO 模拟借口超时，feign配置了重试就会触发重试
            // TimeUnit.SECONDS.sleep(6);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e0) {
            // 重新设置中断状态
            Thread.currentThread().interrupt();
            throw new RuntimeException(e0);
        }
        return proVo;
    }
}
