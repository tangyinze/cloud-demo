package com.tyz.order.interceptor;

import feign.InvocationContext;
import feign.Response;
import feign.ResponseInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import java.nio.charset.StandardCharsets;
import java.util.Objects;


/**
 * @program: cloud-demo
 * @description: feign 响应拦截器处理
 * @author: tyz
 * @create: 2025-03-15
 */
@Component
public class UserFeignResponseInterceptor implements ResponseInterceptor {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserFeignResponseInterceptor.class);

    /**
     * @param invocationContext 执行上下文
     * @param chain             拦截器链
     * @return object
     * @throws Exception 异常
     */
    @Override
    public Object intercept(InvocationContext invocationContext, Chain chain) throws Exception {
        // 拦截器链 不能直接操作Response 中的流，因为它只能读一次
        /* try (Response response = invocationContext.response();) {*/
       /* Response response = invocationContext.response();
        if (Objects.nonNull(response) && Objects.nonNull(response.body())) {
            String result = StreamUtils.copyToString(response.body().asInputStream(), StandardCharsets.UTF_8);
            LOGGER.info("feign resp:{}", result);
           // response.toBuilder().body(result,StandardCharsets.UTF_8).build();

        }*/
        /*  }*/
        return chain.next(invocationContext);
    }
}
