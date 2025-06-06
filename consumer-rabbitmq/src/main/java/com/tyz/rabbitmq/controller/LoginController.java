package com.tyz.rabbitmq.controller;

import com.tyz.rabbitmq.config.jwt.JwtUtils;
import com.tyz.rabbitmq.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @program: cloud-demo
 * @description: LoginController
 * @author: tyz
 * @create: 2025-05-17
 */
@RestController
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private JwtUtils jwtUtils;
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserVO user) {
        if (Objects.nonNull(user)) {
            Map<String,String> payload =  new HashMap<>(4);
            payload.put("id", Long.toString(user.getId()));
            payload.put("name", user.getName());
            // 生成JWT的令牌
            String token = jwtUtils.createToken(payload);
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + token)
                    .body(Map.of("token", token, "username", user.getName()));
        }
        return ResponseEntity.status(401).build();
    }
}
