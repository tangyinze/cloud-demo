package com.tyz.rabbitmq.interceptor;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.tyz.rabbitmq.common.UserContextHolder;
import com.tyz.rabbitmq.config.jwt.JwtUtils;
import com.tyz.rabbitmq.vo.UserVO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;


/**
 * <p>
 *     JwtInterceptor maven依赖spring-boot-starter-web
 * </p>
 * @program: cloud-demo
 * @description: JwtInterceptor
 * @author: tyz
 * @create: 2025-05-17
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtils jwtUtils;

    /**
     *
     * @param request 请求体
     * @param response 返回体
     * @param handler 拦截处理器
     * @return boolean true false
     * @throws Exception 异常信息
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");
        if (Objects.isNull(authHeader) || !StringUtils.startsWith(authHeader,"Bearer ") ) {
            response.setStatus(401);
            return false;
        }
        try {
            String token = StringUtils.substring(authHeader,7);
            // 验证令牌
            DecodedJWT decodedJwt = jwtUtils.verifyToken(token);
            // 验证成功，放行请求 存储用户标识
            request.setAttribute("appId", decodedJwt.getSubject());
            String id = decodedJwt.getClaim("id").asString();
            UserVO userVO = new UserVO();
            userVO.setId(Long.parseLong(id));
            // 绑定用户信息到当前线程 后续使用
            UserContextHolder.set(userVO);
            return true;
        } catch (TokenExpiredException e) {
            response.setStatus(403);
            return false;
        } catch (Exception e) {
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 请求结束后清理
        UserContextHolder.clear();
    }
}
