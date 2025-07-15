package com.tyz.rabbitmq.enums;

import com.tyz.rabbitmq.config.valid.CheckEnumValue;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @program: cloud-demo
 * @description: GenderEnum
 * @author: tyz
 * @create: 2025-07-15
 */
public enum StatusEnum implements CheckEnumValue<Integer> {
    /**
     * <p>
     *     状态禁用 0
     * </p>
     */
    DISABLE(0, "禁用"),
    /**
     * <p>
     *     状态启用 1
     * </p>
     */
    ENABLE(1, "启用"),
    /**
     * <p>
     *     状态逻辑删除 -1
     * </p>
     */
    DEL(-1, "逻辑删除");

    /**
     * <p>
     *     枚举code
     * </p>
     */
    private Integer code;

    /**
     * <p>
     *     枚举名称
     * </p>
     */
    private String name;

    StatusEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    /**
     * <p>
     *     获取枚举值值列表
     * </p>
     *
     * @return list
     */
    @Override
    public List<Integer> getEnumValue() {
        return Stream.of(StatusEnum.values())
                .map(StatusEnum::getCode)
                .collect(Collectors.toList());
    }
}

