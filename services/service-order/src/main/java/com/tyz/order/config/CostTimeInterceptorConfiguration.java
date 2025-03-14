package com.tyz.order.config;

import com.tyz.order.interceptor.RequestCostTimeHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @program: cloud-demo
 * @description: 耗时拦截器注册
 * @author: tyz
 * @create: 2025-03-15
 */
@Configuration
@Order(value = Integer.MIN_VALUE)
public class CostTimeInterceptorConfiguration implements WebMvcConfigurer {
    private final RequestCostTimeHandlerInterceptor requestCostTimeHandlerInterceptor;
    @Autowired
    public CostTimeInterceptorConfiguration(RequestCostTimeHandlerInterceptor requestCostTimeHandlerInterceptor) {
        this.requestCostTimeHandlerInterceptor = requestCostTimeHandlerInterceptor;
    }

    /**
     * @param registry 注册拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加自定义的拦截器
        registry.addInterceptor(requestCostTimeHandlerInterceptor)
                // 设置拦截的路径
                .addPathPatterns("/**")
                // 设置不拦截的路径（排除路径）
                .excludePathPatterns("/test");
    }
}
