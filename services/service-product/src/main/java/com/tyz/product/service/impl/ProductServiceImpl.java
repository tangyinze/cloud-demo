package com.tyz.product.service.impl;

import com.tyz.procuct.bean.ProductVO;
import com.tyz.product.service.IProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @program: cloud-demo
 * @description: ProductServiceImpl 商品服务
 * @author: tyz
 * @create: 2025-03-12
 */
@Service
public class ProductServiceImpl implements IProductService {
    /**
     * 通过商品ID获取商品信息
     *
     * @param productId 商品ID
     * @return ProductVO 商品信息
     */
    @Override
    public ProductVO getProductById(Long productId) {
        ProductVO vo = new ProductVO();
        vo.setId(productId);
        vo.setProductName("name-prd-" + productId.toString());
        vo.setNum(1);
        vo.setPrice(new BigDecimal("1.00"));
        return vo;
    }
}
