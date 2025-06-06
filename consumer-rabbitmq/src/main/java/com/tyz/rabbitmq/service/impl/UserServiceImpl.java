package com.tyz.rabbitmq.service.impl;

import com.tyz.rabbitmq.entity.IdCardVO;
import com.tyz.rabbitmq.entity.UserVO;
import com.tyz.rabbitmq.mapper.IdCardMapper;
import com.tyz.rabbitmq.mapper.UserMapper;
import com.tyz.rabbitmq.service.IIdCardService;
import com.tyz.rabbitmq.service.IUserService;
import com.tyz.rabbitmq.utils.IDCardGenerator;
import com.tyz.rabbitmq.vo.UserIdCardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @program: cloud-demo
 * @description: UserServiceImpl
 * @author: tyz
 * @create: 2025-06-05
 */
@Service
public class UserServiceImpl implements IUserService {
    private final UserMapper userMapper;

    private final IIdCardService idCardService;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,IIdCardService idCardService) {
        this.userMapper = userMapper;
        this.idCardService = idCardService;
    }

    /**
     * <p>
     *     单个保存用户信息
     * </p>
     *
     * @param vo 用户
     * @return vo
     */
    @Override
    public UserVO saveOne(UserVO vo) {
        vo.setStatus(1);
        vo.setCreateTime(new Date());
        vo.setLastLoginTime(vo.getCreateTime());
        vo.setLastUpdateTime(vo.getCreateTime());
        userMapper.insert(vo);
        return vo;
    }

    /**
     * @param user  用户
     * @return vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public UserVO saveUserIdCard(UserVO user) {
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setLastLoginTime(user.getCreateTime());
        user.setLastUpdateTime(user.getCreateTime());
        userMapper.insert(user);
        IdCardVO vo = new IdCardVO();
        vo.setStatus(1);
        vo.setCreateTime(user.getCreateTime());
        vo.setIdCardNo(IDCardGenerator.generateIdCardNo());
        vo.setUserId(user.getId());
        vo.setLastLoginTime(vo.getCreateTime());
        vo.setLastUpdateTime(vo.getCreateTime());
        idCardService.saveOne(vo);
        return user;
    }

    /**
     * @param userId userId
     * @return ov
     */
    @Override
    public UserIdCardVO findUserCardInfo(Long userId) {
        return userMapper.findUserCardInfo(userId);
    }
}
