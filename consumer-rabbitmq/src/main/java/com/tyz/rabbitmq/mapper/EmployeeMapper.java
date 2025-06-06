package com.tyz.rabbitmq.mapper;

import com.tyz.rabbitmq.entity.EmployeeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @program: cloud-demo
 * @description: t_employee mapper
 * @author: tyz
 * @create: 2025-06-06
 */
@Mapper
public interface EmployeeMapper {
    EmployeeVO selectById(@Param("id") Long id);

    int insert(@Param("vo") EmployeeVO employeeVO);

    int update(@Param("vo") EmployeeVO employeeVO);

    int delete(@Param("id") Long id);

    int batchSmallInsert(@Param("list") List<EmployeeVO> employees);
}
