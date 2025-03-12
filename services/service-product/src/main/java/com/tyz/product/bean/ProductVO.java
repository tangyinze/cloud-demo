package com.tyz.product.bean;

import java.math.BigDecimal;

/**
 * @program: cloud-demo
 * @description: 商品VO信息
 * @author: tyz
 * @create: 2025-03-12
 */
public class ProductVO {
    /**
     * 商品id
     */
    private Long id;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 商品数量
     */
    private int num;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ProductVO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", num=" + num +
                '}';
    }
}
