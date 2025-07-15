package com.tyz.rabbitmq.config.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValueValidator.class})
public @interface EnumValue {
    String message() default "enum value is not valid";

    /** 关联的枚举类  CheckEnumValue是我们定义的公共枚举接口，所以枚举类都要实现提供返回枚举值的方法   */
    Class<? extends CheckEnumValue> linkEnum() default CheckEnumValue.class;

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}