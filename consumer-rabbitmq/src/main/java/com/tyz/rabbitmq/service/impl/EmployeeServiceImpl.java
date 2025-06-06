package com.tyz.rabbitmq.service.impl;

import com.tyz.rabbitmq.entity.EmployeeVO;
import com.tyz.rabbitmq.mapper.EmployeeMapper;
import com.tyz.rabbitmq.service.IEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @program: cloud-demo
 * @description: EmployeeService
 * @author: tyz
 * @create: 2025-06-06
 */
@Service
public class EmployeeServiceImpl implements IEmployeeService {
    private final EmployeeMapper employeeMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeMapper employeeMapper) {
        this.employeeMapper = employeeMapper;
    }

    /**
     * @param vo vo
     * @return vo
     */
    @Override
    public EmployeeVO saveOne(EmployeeVO vo) {
        employeeMapper.insert(vo);
        return vo;
    }

    /**
     * @param empList  信息
     * @return int
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public int batchSave(List<EmployeeVO> empList) {
        return employeeMapper.batchSmallInsert(empList);
    }
}
