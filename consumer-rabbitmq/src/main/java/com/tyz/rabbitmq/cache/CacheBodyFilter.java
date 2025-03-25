package com.tyz.rabbitmq.cache;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @program: cloud-demo
 * @description: 过滤器来缓存request
 * @author: tyz
 * @create: 2025-03-25
 */
@Component
public class CacheBodyFilter implements Filter {
    /**
     * @param servletRequest  请求体
     * @param servletResponse 返回体
     * @param filterChain     过滤器链
     * @throws IOException      IO异常
     * @throws ServletException Servlet异常
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        // 缓存了请求体 此处判断是为了缩小影响范围，本身RepeatedlyReadRequestWrapper只是针对HttpServletRequest，不进行判断可能会影响其他类型的请求
        if (servletRequest instanceof HttpServletRequest) {
            // 将默认的HttpServletRequest转换为自定义的RepeatedlyReadRequestWrapper
            RepeatedlyReadRequestWrapper wrappedRequest = new RepeatedlyReadRequestWrapper((HttpServletRequest) servletRequest);
            // 将转换后的request传递至调用链中
            filterChain.doFilter(wrappedRequest, servletResponse);
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}
