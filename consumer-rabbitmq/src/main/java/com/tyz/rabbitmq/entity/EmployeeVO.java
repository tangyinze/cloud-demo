package com.tyz.rabbitmq.entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: cloud-demo
 * @description: EmployeeVO
 * @author: tyz
 * @create: 2025-06-06
 */
public class EmployeeVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3904553796368055308L;
    /**
     * 员工ID
     */
    private Long empId;

    /**
     * 员工名称
     */
    private String empName;
    /**
     * 员工编号
     */
    private String empNo;
    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 状态，-1：逻辑删除，0：禁用，1：启用
     */
    private Integer status;

    public Long getEmpId() {
        return empId;
    }

    public void setEmpId(Long empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "EmployeeVO{" +
                "empId=" + empId +
                ", empName='" + empName + '\'' +
                ", empNo='" + empNo + '\'' +
                ", deptId=" + deptId +
                ", status=" + status +
                '}';
    }
}
