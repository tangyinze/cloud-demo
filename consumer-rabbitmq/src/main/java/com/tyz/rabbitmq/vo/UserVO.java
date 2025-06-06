package com.tyz.rabbitmq.vo;

/**
 * @program: cloud-demo
 * @description: UserVo
 * @author: tyz
 * @create: 2025-05-17
 */
public class UserVO {
    private Long id;
    private String name;

    private String userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserVO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}
