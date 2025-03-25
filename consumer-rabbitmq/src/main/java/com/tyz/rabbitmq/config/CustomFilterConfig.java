package com.tyz.rabbitmq.config;

import com.tyz.rabbitmq.cache.CacheBodyFilter;
import jakarta.servlet.Filter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * @program: cloud-demo
 * @description: 注册过滤器
 * @author: tyz
 * @create: 2025-03-25
 */
@Configuration
public class CustomFilterConfig {
    @Bean
    public FilterRegistrationBean<Filter> customCacheBodyFilter() {
        FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new CacheBodyFilter());
        registrationBean.setUrlPatterns(List.of("/*"));
        // 显示申明过滤器的顺序，越小越先执行
        registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registrationBean;
    }
}
