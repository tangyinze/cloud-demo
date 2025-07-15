package com.tyz.rabbitmq.config.valid;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * <p>
 *     默认情况下，参数校验会把所有规则都检查一遍，等全部检查完才告诉你哪里错了。
 *     这种方式效率不高，尤其在有很多规则的时候。
 *     我们可以通过开启“快速失败”模式来优化：一旦发现错误，就立刻停止后续检查。
 *     快速失败配置 方式如下：
 * </p>
 *
 * @program: cloud-demo
 * @description: ValidConfig
 * @author: tyz
 * @create: 2025-07-14
 */
@Configuration
public class ValidConfig {
    /**
     * 快速返回校验器
     * @return Validator
     */
    @Bean
    public Validator validator(){
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                //快速失败模式
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }

    /**
     * 设置快速校验，返回方法校验处理器
     * 使用MethodValidationPostProcessor注入后，会启动自定义校验器
     * @return MethodValidationPostProcessor
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor(){
        MethodValidationPostProcessor methodValidationPostProcessor = new MethodValidationPostProcessor();
        methodValidationPostProcessor.setValidator(validator());
        return methodValidationPostProcessor;
    }
}
