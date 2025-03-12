package com.tyz.product.service;

import com.tyz.product.bean.ProductVO;

/**
 * @author tyz
 */
public interface IProductService {

    /**
     *  通过商品ID获取商品信息
     * @param productId 商品ID
     * @return ProductVO 商品信息
     */
    ProductVO getProductById(Long productId);
}
