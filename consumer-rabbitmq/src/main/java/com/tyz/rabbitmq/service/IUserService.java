package com.tyz.rabbitmq.service;

import com.tyz.rabbitmq.entity.UserVO;
import com.tyz.rabbitmq.vo.UserIdCardVO;

/**
 * @program: cloud-demo
 * @description: t_user entity vo
 * @author: tyz
 * @create: 2025-06-05
 */
public interface IUserService {
    UserVO saveOne(UserVO vo);

    UserVO saveUserIdCard(UserVO user);

    UserIdCardVO findUserCardInfo(Long userId);

}
