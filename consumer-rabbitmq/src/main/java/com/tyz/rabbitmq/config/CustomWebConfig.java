package com.tyz.rabbitmq.config;

import com.tyz.rabbitmq.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: cloud-demo
 * @description: WebConfig
 * @author: tyz
 * @create: 2025-05-17
 */
@Configuration
public class CustomWebConfig implements WebMvcConfigurer {
    @Autowired
    private  JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                // 添加拦截路径
                .addPathPatterns("/api/**")
                // 添加放行路径
                .excludePathPatterns("/api/login");
    }
}
