package com.tyz.rabbitmq.common;

import com.tyz.rabbitmq.vo.UserVO;

/**
 * @program: cloud-demo
 * @description: UserContextHolder
 * @author: tyz
 * @create: 2025-05-17
 */
public class UserContextHolder {
    private static final ThreadLocal<UserVO> CONTEXT = new ThreadLocal<>();

    public static void set(UserVO user) {
        CONTEXT.set(user);
    }

    public static UserVO get() {
        return CONTEXT.get();
    }

    public static void clear() {
        // 必须显式清理防止内存泄漏
        CONTEXT.remove();
    }
}