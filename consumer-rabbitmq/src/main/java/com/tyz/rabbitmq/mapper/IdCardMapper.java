package com.tyz.rabbitmq.mapper;

import com.tyz.rabbitmq.entity.IdCardVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: cloud-demo
 * @description: t_id_card entity vo
 * @author: tyz
 * @create: 2025-06-06
 */
@Mapper
public interface IdCardMapper {
    IdCardVO selectById(@Param("id") Long id);
    int insert(@Param("vo") IdCardVO cardVO);
    int update(@Param("vo") IdCardVO cardVO);
    int delete(@Param("id") Long id);
    List<IdCardVO> selectAll();

}
