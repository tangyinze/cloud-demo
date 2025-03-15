package com.tyz.order.interceptor;

import com.alibaba.fastjson.JSON;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.collections4.MapUtils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

/**
 * @program: cloud-demo
 * @description: 客至化Fegin的请求拦截处理
 * @author: tyz
 * @create: 2025-03-15
 */
@Component
public class UserFeignRequestInterceptor implements RequestInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFeignRequestInterceptor.class);

    /**
     * 发送前的处理
     *
     * @param requestTemplate 请求参数
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        // 例如可以加一些请求头参数
        requestTemplate.header("user-token", UUID.randomUUID().toString());
        LOGGER.info("请求url:{},param参数:{},body参数:{}",
                requestTemplate.url(),
                getRequestParamAsString(requestTemplate),
                getRequestBodyAsString(requestTemplate));

    }

    /**
     *  获取请求体里的参数
     * @param template 请求模板
     * @return string
     */
    private String getRequestBodyAsString(RequestTemplate template) {
        if (Objects.nonNull(template) && Objects.nonNull(template.body())) {
            return new String(template.body(), StandardCharsets.UTF_8);
        } else {
            return StringUtils.EMPTY;
        }
    }

    /**
     * 获取请求url里的参数
     * @param template 请求模板
     * @return string
     */
    private String getRequestParamAsString(RequestTemplate template) {
        if (Objects.nonNull(template) && MapUtils.isNotEmpty(template.queries())) {
            return JSON.toJSONString(template.queries());
        } else {
            return StringUtils.EMPTY;
        }
    }

}
