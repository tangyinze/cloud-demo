package com.tyz.rabbitmq.vo;

import com.tyz.rabbitmq.entity.DeptVO;
import com.tyz.rabbitmq.entity.EmployeeVO;

import java.io.Serial;
import java.util.List;

/**
 * @program: cloud-demo
 * @description: DeptEmpVO
 * @author: tyz
 * @create: 2025-06-06
 */
public class DeptEmpVO extends DeptVO {

    @Serial
    private static final long serialVersionUID = 386659923844977002L;

    private List<EmployeeVO> employees;

    public List<EmployeeVO> getEmployees() {
        return employees;
    }

    public void setEmployees(List<EmployeeVO> employees) {
        this.employees = employees;
    }
}
