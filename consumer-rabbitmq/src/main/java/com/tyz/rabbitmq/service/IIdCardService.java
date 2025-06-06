package com.tyz.rabbitmq.service;

import com.tyz.rabbitmq.entity.IdCardVO;

/**
 * @program: cloud-demo
 * @description: t_id_card entity vo
 * @author: tyz
 * @create: 2025-06-05
 */
public interface IIdCardService {
    IdCardVO saveOne(IdCardVO vo);
}
