package com.tyz.rabbitmq.config.valid;

import java.util.List;

/**
 * <p>
 * 自定义注解枚举 统一接口来获取枚举值
 * </p>
 *
 * @program: cloud-demo
 * @description: CheckEnumValue
 * @author: tyz
 * @create: 2025-07-14
 */
public interface CheckEnumValue<T> {
    List<T> getEnumValue();
}
