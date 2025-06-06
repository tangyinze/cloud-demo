package com.tyz.rabbitmq.config.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: cloud-demo
 * @description: JwtUtils
 * @author: tyz
 * @create: 2025-05-16
 */
@Configuration
@EnableConfigurationProperties(CustomJwtConfig.class)
// @ConditionalOnProperty(prefix = "jwt", name = "enabled", havingValue = "true")
public class JwtUtils {
    @Autowired
    private CustomJwtConfig customJwtConfig;

    /**
     * <p>
     *     生成jwt token
     * </p>
     * @param claimMap 负载里的内容
     * @return string jwt token string
     */
    public String createToken(Map<String, String> claimMap) {
        // 设置JWT头部 默认也是
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        // 创建token 使用Lambda创建payload
        JWTCreator.Builder builder = JWT.create();
        for (Map.Entry<String, String> entry : claimMap.entrySet()) {
            builder.withClaim(entry.getKey(), entry.getValue());
        }
        // 添加头部，可省略保持默认，默认即map中的键值对
        return builder.withHeader(map)
                .withSubject(customJwtConfig.getSubject())
                .withIssuer(customJwtConfig.getIssuer())
                // 设置过期时间
                .withExpiresAt(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).plusMillis(customJwtConfig.getExpiration()))
                // 设置签名解码算法
                .sign(Algorithm.HMAC256(customJwtConfig.getSecret().getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * <p>
     *     验证 jwt token
     * </p>
     * @param token token
     * @return DecodedJWT  decodedJWT
     */
    public DecodedJWT verifyToken(String token) {
        return JWT.require(Algorithm.HMAC256(customJwtConfig.getSecret().getBytes(StandardCharsets.UTF_8)))
                .build()
                .verify(token);
    }
}
