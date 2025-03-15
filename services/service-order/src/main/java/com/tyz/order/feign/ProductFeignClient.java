package com.tyz.order.feign;

import com.tyz.procuct.bean.ProductVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * 商品的feign 客户端
 *
 * @author tyz
 */
@FeignClient(value = "service-product")
public interface ProductFeignClient {
    @GetMapping("/product/{id}")
    ProductVO getProductById(@PathVariable("id") Long productId);
}
