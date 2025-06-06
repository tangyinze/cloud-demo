package com.tyz.rabbitmq.vo;

import com.tyz.rabbitmq.entity.IdCardVO;
import com.tyz.rabbitmq.entity.UserVO;

import java.io.Serial;

/**
 * @program: cloud-demo
 * @description: UserIdCardVO
 * @author: tyz
 * @create: 2025-06-06
 */
public class UserIdCardVO extends UserVO {
    @Serial
    private static final long serialVersionUID = 1725469890234763493L;
    private IdCardVO card;

    public IdCardVO getCard() {
        return card;
    }

    public void setCard(IdCardVO card) {
        this.card = card;
    }
}
