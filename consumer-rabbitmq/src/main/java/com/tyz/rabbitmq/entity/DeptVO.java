package com.tyz.rabbitmq.entity;

import java.io.Serial;
import java.io.Serializable;

/**
 * @program: cloud-demo
 * @description: DeptVO
 * @author: tyz
 * @create: 2025-06-06
 */
public class DeptVO implements Serializable {
    @Serial
    private static final long serialVersionUID = -2897167410936918439L;
    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 部门名
     */
    private String deptName;

    /**
     * 部门号
     */
    private String deptNo;

    /**
     * 状态，-1：逻辑删除，0：禁用，1：启用
     */
    private Integer status;

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptNo() {
        return deptNo;
    }

    public void setDeptNo(String deptNo) {
        this.deptNo = deptNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
