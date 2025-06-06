package com.tyz.rabbitmq.service;

import com.tyz.rabbitmq.entity.DeptVO;
import com.tyz.rabbitmq.vo.DeptEmpVO;

/**
 * @program: cloud-demo
 * @description: IDeptService
 * @author: tyz
 * @create: 2025-06-05
 */
public interface IDeptService {
    DeptVO save(DeptVO vo);

    DeptEmpVO saveLinkEmp(DeptEmpVO deptEmpVO);

    DeptEmpVO findDepEmpInfo(Long id);
}
