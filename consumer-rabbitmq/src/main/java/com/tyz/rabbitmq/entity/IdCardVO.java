package com.tyz.rabbitmq.entity;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * @program: cloud-demo
 * @description: IdCardVO
 * @author: tyz
 * @create: 2025-06-06
 */
public class IdCardVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 464169513752961479L;
    /**
     * 主键
     */
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 身份证号
     */
    private String idCardNo;

    /**
     * 状态，-1：逻辑删除，0：禁用，1：启用
     */
    private Integer status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上次登录时间
     */
    private Date lastLoginTime;

    /**
     * 上次更新时间
     */
    private Date lastUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    @Override
    public String toString() {
        return "IdCardVO{" +
                "id=" + id +
                ", userId=" + userId +
                ", idCardNo='" + idCardNo + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", lastLoginTime=" + lastLoginTime +
                ", lastUpdateTime=" + lastUpdateTime +
                '}';
    }
}
