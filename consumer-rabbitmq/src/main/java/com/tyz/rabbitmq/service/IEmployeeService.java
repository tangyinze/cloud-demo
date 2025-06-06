package com.tyz.rabbitmq.service;

import com.tyz.rabbitmq.entity.EmployeeVO;

import java.util.List;

/**
 * @program: cloud-demo
 * @description: t_user entity vo
 * @author: tyz
 * @create: 2025-06-05
 */
public interface IEmployeeService {
    EmployeeVO saveOne(EmployeeVO vo);

    int batchSave(List<EmployeeVO> empList);
}
