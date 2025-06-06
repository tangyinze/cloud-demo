package com.tyz.rabbitmq.mapper;

import com.tyz.rabbitmq.entity.DeptVO;
import com.tyz.rabbitmq.vo.DeptEmpVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @program: cloud-demo
 * @description: t_department entity vo
 * @author: tyz
 * @create: 2025-06-06
 */
@Mapper
public interface DeptMapper {
    DeptVO selectById(@Param("id") Long id);
    int insert(@Param("vo") DeptVO deptVO);
    int update(@Param("vo") DeptVO deptVO);
    int delete(@Param("id") Long deptId);

    DeptEmpVO findDepEmpIno(@Param("id") Long deptId);
}
