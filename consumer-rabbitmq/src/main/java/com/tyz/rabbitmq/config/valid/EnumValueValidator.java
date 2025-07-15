package com.tyz.rabbitmq.config.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * @program: cloud-demo
 * @description: EnumValueValidator
 * @author: tyz
 * @create: 2025-07-15
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
    private Class<? extends CheckEnumValue> clz;

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        clz = constraintAnnotation.linkEnum();
    }

    /**
     * <p>
     *     枚举校验逻辑
     * </p>
     *
     * @param value 值
     * @param context 验证下下文
     * @return true|false
     */
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        // 参数值为空不校验
        if (Objects.isNull(value)) {
            return true;
        }
        // 关联的不是枚举类不校验
        if (!clz.isEnum()) {
            return true;
        }
        CheckEnumValue[] enumConstants = clz.getEnumConstants();
        if (Objects.isNull(enumConstants) || enumConstants.length == 0) {
            return true;
        }
        CheckEnumValue enumConstant = enumConstants[0];
        List enumValue = enumConstant.getEnumValue();
        // 判断参数值是否在枚举值中
        if (CollectionUtils.isEmpty(enumValue)) {
            return true;
        }
        if (enumValue.contains(value)) {
            return true;
        }
        return false;
    }
}
