package com.tyz.rabbitmq.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

/**
 * @program: cloud-demo
 * @description: redis 生成订单的id
 * @author: tyz
 * @create: 2025-04-03
 */
@Component
public class OrderRedisGenerateId {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRedisGenerateId.class);

    private static final DateTimeFormatter DT_FMT = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    private final RedisTemplate<String, Object> redisTemplate;

    public OrderRedisGenerateId(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 时间精确到3位毫秒+7位数找自增数字
     * redis创建一个key 进行自增，然后30s过期与配合数据库锁常归都能基本满足了
     *
     * @return string orderId
     */
    public String generateOrderId() {
        String currentDateTime = LocalDateTime.now().format(DT_FMT);
        Long count = getAndExpireCounterByLua(currentDateTime);
        String format = String.format("%07d", count);
        return currentDateTime + format;
    }

    private Long getAndExpireCounterByLua(String key){
        // 定义Lua脚步 也可以定义为静态加快并行能力
        StringBuilder sb = new StringBuilder();
        sb.append("if redis.call('EXISTS',KEYS[1]) == 0 then ");
        sb.append("   redis.call('SET', KEYS[1], 1) ");
        sb.append("   redis.call('EXPIRE', KEYS[1], 30) ");
        sb.append("  return 1  ");
        sb.append("else ");
        sb.append("  return redis.call('INCR', KEYS[1]) ");
        sb.append("end ");
        // LOGGER.info("redisScript:{}", sb);
        // 使用Redis的脚本执行功能
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptText(sb.toString());
        redisScript.setResultType(Long.class);
        Long execute = redisTemplate.execute(redisScript, Collections.singletonList(key));
        sb.setLength(0);
        sb.trimToSize();
        return execute;
    }
}
