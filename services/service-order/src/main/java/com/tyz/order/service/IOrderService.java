package com.tyz.order.service;

import com.tyz.order.bean.OrderVO;

/**
 * @author tyz
 */
public interface IOrderService {
    /**
     *  根据商品ID和用户Id 来创建订单
     * @param productId 商品ID
     * @param userId 用户Id
     * @return OrderVO 订单信息
     */
    OrderVO crateOrder(Long productId, Long userId);
}
