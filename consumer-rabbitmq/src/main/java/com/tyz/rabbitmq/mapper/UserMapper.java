package com.tyz.rabbitmq.mapper;

import com.tyz.rabbitmq.entity.UserVO;
import com.tyz.rabbitmq.vo.UserIdCardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: cloud-demo
 * @description: t_user entity vo
 * @author: tyz
 * @create: 2025-06-05
 */
@Mapper
public interface UserMapper {
    UserVO selectById(@Param("id") Long id);
    int insert(@Param("vo") UserVO user);
    int update(@Param("vo") UserVO user);
    int delete(@Param("id") Long id);
    List<UserVO> selectAll();

    UserIdCardVO findUserCardInfo(@Param("id") Long userId);
}
