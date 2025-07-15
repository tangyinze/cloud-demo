package com.tyz.rabbitmq.config.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * <p>
 * 自定义注解手机号的验证
 * </p>
 *
 * @program: cloud-demo
 * @description: PhoneValidator
 * @author: tyz
 * @create: 2025-07-14
 */
public class PhoneValidator implements ConstraintValidator<PhoneValid, Object> {
    /**
     * 11位手机号的正则表达式,以13、14、15、17、18头
     * ^：匹配字符串的开头
     * 13\d：匹配以13开头的手机号码
     * 14[579]:匹配以145、147、149开头的手机号
     * 15[^4\D]：匹配以15开头且第3位数字不为4的手机号码
     * 17[^49\D]:匹配以17开头且第3位数字不为4或9的手机号码
     * 18\d :匹配以18开头的手机号码
     * \d{8}：匹配手机号码的后8位，即剩余的8个数字
     * $：匹配字符串的结尾
     */
    public static final String REGEX_PHONE = "^(13\\d|14[579]|15[^4\\D]|17[^49\\D]|18\\d)\\d{8}$";

    /**
     * <p>
     * 初始化注解
     * </p>
     *
     * @param constraintAnnotation 注解
     */
    @Override
    public void initialize(PhoneValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * <p>
     * 校验参数，true表示校验通过，false表示校验失败
     * </p>
     *
     * @param o
     * @param constraintValidatorContext 注解
     * @return true or false
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String phone = String.valueOf(o);
        if (phone.length() != 11) {
            return false;
        }
        //正则校验
        return phone.matches(REGEX_PHONE);
    }
}
