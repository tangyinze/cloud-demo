package com.tyz.order.bean;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: cloud-demo
 * @description: Order 订单信息VO
 * @author: tyz
 * @create: 2025-03-12
 */
public class OrderVO {
    /**
     * 订单ID
     */
    private Long id;
    /**
     * 订单总额
     */
    private BigDecimal totalAmount;
    /**
     * 下单用户ID
     */
    private Long userId;
    /**
     * 用户名称
     */
    private String nickName;
    /**
     * 配送地址
     */
    private String address;
    /**
     * 1：N
     * 订单下的商品
     */
    private List<Object> productList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Object> getProductList() {
        return productList;
    }

    public void setProductList(List<Object> productList) {
        this.productList = productList;
    }

    @Override
    public String toString() {
        return "OrderVO{" +
                "id=" + id +
                ", totalAmount=" + totalAmount +
                ", userId=" + userId +
                ", nickName='" + nickName + '\'' +
                ", address='" + address + '\'' +
                ", productList=" + productList +
                '}';
    }
}
