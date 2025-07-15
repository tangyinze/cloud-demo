package com.tyz.rabbitmq.config.valid;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 自定义注解手机号的验证
 * </p>
 *
 * @program: cloud-demo
 * @description: PhoneValid
 * @author: tyz
 * @create: 2025-07-14
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Constraint(validatedBy = PhoneValidator.class)
public @interface PhoneValid {
    String message() default "请填写正确的手机号";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

