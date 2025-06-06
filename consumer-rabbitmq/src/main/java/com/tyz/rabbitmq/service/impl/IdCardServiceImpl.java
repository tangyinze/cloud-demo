package com.tyz.rabbitmq.service.impl;

import com.tyz.rabbitmq.entity.IdCardVO;
import com.tyz.rabbitmq.mapper.IdCardMapper;
import com.tyz.rabbitmq.service.IIdCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: cloud-demo
 * @description: IdCardService
 * @author: tyz
 * @create: 2025-06-06
 */
@Service
public class IdCardServiceImpl implements IIdCardService {
    private final IdCardMapper idCardMapper;

    @Autowired
    public IdCardServiceImpl(IdCardMapper idCardMapper) {
        this.idCardMapper = idCardMapper;
    }

    /**
     * @param vo 卡信息
     * @return vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public IdCardVO saveOne(IdCardVO vo) {
        idCardMapper.insert(vo);
        return vo;
    }
}
