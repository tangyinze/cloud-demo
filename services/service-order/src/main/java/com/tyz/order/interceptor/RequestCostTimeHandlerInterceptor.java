package com.tyz.order.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: cloud-demo
 * @description: 请求耗时计算拦截器
 * @author: tyz
 * @create: 2025-03-15
 */
@Component
public class RequestCostTimeHandlerInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestCostTimeHandlerInterceptor.class);

    private static final String BEGIN_TIME = "BEGIN_TIME";
    // 使用线程变量来记开始时间
    private static final ThreadLocal<Map<String, Object>> CONTEXTS = new ThreadLocal<>();

    /**
     * 设置变量key-value
     * @param key 变量名
     * @param value 变量值
     */
    private static void setKey(String key, Object value) {
        // 获取不到会给初始ThreadLocalMap
        if (Objects.isNull(CONTEXTS.get())) {
            CONTEXTS.set(new HashMap<String, Object>(4));
        }
        CONTEXTS.get().put(key, value);
    }

    /**
     * 获取变更值
     * @param key 变量名
     * @return 变量值
     */
    private static Object getKey(String key) {
        return CONTEXTS.get().get(key);
    }

    /**
     * <p>清空线程变更</p>
     */
    private static void clear() {
        CONTEXTS.remove();
    }

    /**
     * 请求到达拦截器时制行
     * @param request 请求
     * @param response 响应
     * @param handler 处理器
     * @return boolean
     * @throws Exception 异常信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long begin = Instant.now().toEpochMilli();
        // 如果你想测量代码的执行时间（以纳秒为单位），可以使用System.nanoTime()，但如果你确实需要获取当前时间的毫秒数，你应该使用System.currentTimeMillis()或Instant.now().toEpochMilli()。
        setKey(BEGIN_TIME, begin);
        // return HandlerInterceptor.super.preHandle(request, response, handler);
        return Boolean.TRUE;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LOGGER.info("Controller执行完后-执行 HandlerInterceptor 逻辑");
    }

    /**
     * 请求业务处理完成，响应返回前执行
     * @param request 请求
     * @param response 响应
     * @param handler 处理器
     * @param ex 异常信息
     * @throws Exception 异常信息
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long begin = (Long) getKey(BEGIN_TIME);
        if (Objects.nonNull(begin)) {
            long costTime = Instant.now().toEpochMilli() - begin;
            StringBuilder sb = new StringBuilder();
            sb.append("接口[").append(request.getRequestURI()).append("]耗时毫秒数：").append(costTime).append("ms");
            if (costTime > 10000) {
                LOGGER.warn(sb.toString());
            } else {
                LOGGER.info(sb.toString());
            }

            // 单独使用setLength(0)可以使位置定位到0 (原有value长度和数据不会变动)，后续使用append方法重新生成字符
            // 同时使用setLength(0)和 trimToSize()可以进行清空数据，减少内存占用
            // 最后进行清空builder，减少内存占用（char[] value长度变为0）
            sb.setLength(0);
            sb.trimToSize();
        }
        clear();
        // HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
