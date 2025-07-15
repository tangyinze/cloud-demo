package com.tyz.rabbitmq.config.valid;

import jakarta.validation.groups.Default;

/**
 * <p>
 *     UserVO 更新和新增时的验证统一分组验证
 * </p>
 *
 * @program: cloud-demo
 * @description: UserGroup
 * @author: tyz
 * @create: 2025-07-14
 */
public class UserGroup {
    // 定义一个用于创建用户的校验分组
    public interface CreateGroup extends Default {
    }

    // 定义一个用于更新用户的校验分组
    public interface UpdateGroup extends Default {
    }
}
