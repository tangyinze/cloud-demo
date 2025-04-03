package com.tyz.rabbitmq.common;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: cloud-demo
 * @description: Redis 统一工具类封装
 * @author: tyz
 * @create: 2025-04-03
 */
@Component
public class RedisUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisUtil.class);
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 在 Spring Boot 中，RedisTemplate 是由 Spring 自动配置的。
     * Spring Boot 的 spring-boot-starter-data-redis 会自动根据 application.yml
     * 或 application.properties 中的 Redis 配置生成
     * RedisTemplate 实例，并将其作为一个 Bean 注入到 Spring 容器中。
     * <p>
     * 从 Spring Framework 4.3 开始，如果类只有一个构造函数，Spring 会自动推断并使用这个构造函数进行依赖注入，无需显式地使用 @Autowired 注解。
     * 由于 RedisUtil 类中只有一个构造函数，因此 Spring 自动会将 RedisTemplate<String, Object> 注入到 RedisUtil 中。
     *
     * @param redisTemplate 模板
     */
    public RedisUtil(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 给一个指定的 key 设置缓存失效时间
     * 对应的指令：EXPIRE key 10
     *
     * @param key  建
     * @param time 过期秒数
     * @return boolean
     */
    public Boolean expire(String key, long time) {
        Boolean expire = redisTemplate.expire(key, time, TimeUnit.SECONDS);
        return Optional.ofNullable(expire).orElse(Boolean.FALSE);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.SECONDS);
        return Optional.ofNullable(expire).orElse(-1L);
    }


    /**
     * 给一个指定的 key 设置缓存失效时间
     *
     * @param key  建
     * @param time 过期毫秒数
     * @return boolean
     */
    public Boolean expireMs(String key, long time) {
        Boolean expire = redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(expire).orElse(Boolean.FALSE);
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpireMs(String key) {
        Long expire = redisTemplate.getExpire(key, TimeUnit.MILLISECONDS);
        return Optional.ofNullable(expire).orElse(-1L);
    }

    /**
     * 判断key是否存在
     * 对应的指令：exists key
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    public void delete(String... key) {
        if (Objects.nonNull(key) && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 获取普通缓存
     * get key
     *
     * @param key 键
     * @return 值
     */
    public Object get(String key) {
        return Objects.isNull(key) ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 泛型方法 get：<T> 使得 get 方法可以返回不同类型的对象，clazz 参数是对象的类型信息，传入后可以将存储的值反序列化为相应类型。
     * Redis 中的数据必须是通过 GenericJackson2JsonRedisSerializer 或类似的 JSON 序列化器存储的，确保可以成功反序列化。
     *
     * @param key   key
     * @param clazz class
     * @param <T>   obj
     * @return obj
     */
    public <T> T getKeyT(String key, Class<T> clazz) {
        try {
            Object value = redisTemplate.opsForValue()
                    .get(key);
            if (value != null) {
                // 自动转换成指定类型
                return clazz.cast(value);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean setKeyVal(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
    }

    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean setKeyValTtl(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
                return true;
            } else {
                return setKeyVal(key, value);
            }
        } catch (Exception e1) {
            LOGGER.error(e1.getMessage(), e1);
            return false;
        }
    }

    /**
     * 批量添加 key (重复的键会覆盖)
     *
     * @param keyAndValue 多个键值
     */
    public void batchSet(Map<String, String> keyAndValue) {
        redisTemplate.opsForValue().multiSet(keyAndValue);
    }

    /**
     * 批量添加 key-value 只有在键不存在时,才添加
     * map 中只要有一个key存在,则全部不添加 这里可能不对
     *
     * @param keyAndValue 多个键值
     */
    public void batchSetIfAbsent(Map<String, String> keyAndValue) {
        redisTemplate.opsForValue().multiSetIfAbsent(keyAndValue);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是长整型 ,将报错
     *
     * @param key    键
     * @param number 步长
     */
    public Long increment(String key, long number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    /**
     * 对一个 key-value 的值进行加减操作,
     * 如果该 key 不存在 将创建一个key 并赋值该 number
     * 如果 key 存在,但 value 不是 纯数字 ,将报错
     *
     * @param key    键
     * @param number 浮点步长
     */
    public Double increment(String key, double number) {
        return redisTemplate.opsForValue().increment(key, number);
    }

    //- - - - - - - - - - - - - - - - - - - - -  set类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 将数据放入set缓存
     *
     * @param key 键
     */
    public void sSet(String key, String value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 获取变量中的值
     *
     * @param key 键
     * @return obj
     */
    public Set<Object> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 随机获取变量中指定个数的元素
     *
     * @param key   键
     * @param count 值
     * @return list
     */
    public List<Object> randomMembers(String key, long count) {
        List<Object> objects = redisTemplate.opsForSet().randomMembers(key, count);
        return Optional.ofNullable(objects).orElse(new ArrayList<>(0));
    }

    /**
     * 随机获取变量中的元素
     *
     * @param key 键
     * @return obj
     */
    public Object randomMember(String key) {
        return redisTemplate.opsForSet().randomMember(key);
    }

    /**
     * 弹出变量中的元素
     *
     * @param key 键
     * @return obj
     */
    public Object pop(String key) {
        return redisTemplate.opsForSet().pop(key);
    }

    /**
     * 获取变量中值的长度
     *
     * @param key 键
     * @return size
     */
    public long size(String key) {
        Long size = redisTemplate.opsForSet().size(key);
        return Optional.ofNullable(size).orElse(0L);
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sHasKey(String key, Object value) {
        Boolean member = redisTemplate.opsForSet().isMember(key, value);
        return Boolean.TRUE.equals(member);
    }

    /**
     * 检查给定的元素是否在变量中。
     *
     * @param key 键
     * @param obj 元素对象
     * @return true 存在 false不存在
     */
    public boolean isMember(String key, Object obj) {
        Boolean member = redisTemplate.opsForSet().isMember(key, obj);
        return Boolean.TRUE.equals(member);
    }

    /**
     * 转移变量的元素值到目的变量。
     *
     * @param key     键
     * @param value   元素对象
     * @param destKey 目标对象
     * @return true 成功 false 失败
     */
    public boolean move(String key, String value, String destKey) {
        Boolean member = redisTemplate.opsForSet().move(key, value, destKey);
        return Boolean.TRUE.equals(member);
    }

    /**
     * 批量移除set缓存中元素
     *
     * @param key    键
     * @param values 值
     * @return true 成功 false 失败
     */
    public boolean remove(String key, Object... values) {
        Long remove = redisTemplate.opsForSet().remove(key, values);
        Long action = Optional.ofNullable(remove).orElse(0L);
        return action.compareTo(0L) > 0 ? Boolean.TRUE : Boolean.FALSE;
    }

    /**
     * 通过给定的key求2个set变量的差值
     *
     * @param key     键
     * @param destKey 键
     * @return set
     */
    public Set<Object> difference(String key, String destKey) {
        return redisTemplate.opsForSet().difference(key, destKey);
    }

    //- - - - - - - - - - - - - - - - - - - - -  hash类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 加入缓存
     *
     * @param key 键
     * @param map key-val 键值对
     */
    public void hAdd(String key, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 获取 key 下的 所有  hash-key 和 value
     *
     * @param key 键
     * @return obj
     */
    public Map<Object, Object> getHashEntries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 验证指定 key 下 有没有指定的 hash-key
     *
     * @param key     键
     * @param hashKey hash key
     * @return true 成功 false 失败
     */
    public boolean hashKey(String key, String hashKey) {
        Boolean hasKey = redisTemplate.opsForHash().hasKey(key, hashKey);
        return Boolean.TRUE.equals(hasKey);
    }

    /**
     * 获取指定key的值string
     *
     * @param key  键
     * @param key2 键
     * @return string
     */
    public String getMapString(String key, String key2) {
        Object target = redisTemplate.opsForHash().get(key, key2);
        if (Objects.nonNull(target)) {
            return target.toString();
        }
        return "";
    }

    /**
     * 获取指定的值Int
     *
     * @param key  键
     * @param key2 键
     * @return int
     */
    public Integer getMapInt(String key, String key2) {
        return (Integer) redisTemplate.opsForHash().get(key, key2);
    }

    /**
     * 弹出元素并删除
     *
     * @param key 键
     * @return str
     */
    public String popValue(String key) {
        Object target = redisTemplate.opsForSet().pop(key);
        if (Objects.nonNull(target)) {
            return target.toString();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 删除指定 hash 的 HashKey
     *
     * @param key      键
     * @param hashKeys 键
     * @return 删除成功的 数量
     */
    public Long delete(String key, String... hashKeys) {
        return redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     * @param key     键
     * @param hashKey 键
     * @param number  步长
     * @return long
     */
    public Long increment(String key, String hashKey, long number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 给指定 hash 的 hashkey 做增减操作
     *
     * @param key     键
     * @param hashKey 键
     * @param number  符点步长
     * @return obj
     */
    public Double increment(String key, String hashKey, Double number) {
        return redisTemplate.opsForHash().increment(key, hashKey, number);
    }

    /**
     * 获取 key 下的 所有 hashkey 字段
     *
     * @param key 键
     * @return obj
     */
    public Set<Object> hashKeys(String key) {
        return redisTemplate.opsForHash().keys(key);
    }

    /**
     * 获取指定 hash 下面的 键值对 数量
     *
     * @param key 键
     * @return long
     */
    public Long hashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    //- - - - - - - - - - - - - - - - - - - - -  list类型 - - - - - - - - - - - - - - - - - - - -

    /**
     * 在变量左边添加元素值
     *
     * @param key   键
     * @param value 值
     * @return long
     */
    public Long leftPush(String key, Object value) {
        Long leftPush = redisTemplate.opsForList().leftPush(key, value);
        return Optional.ofNullable(leftPush).orElse(0L);
    }

    /**
     * 获取集合指定位置的值。
     *
     * @param key   键
     * @param index 元素
     * @return obj
     */
    public Object index(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 获取指定区间的值。
     *
     * @param key   键
     * @param start 开始
     * @param end   结束
     * @return obj
     */
    public List<Object> range(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 把最后一个参数值放到指定集合的第一个出现中间参数的前面，
     * 如果中间参数值存在的话。
     *
     * @param key   键
     * @param pivot 指定元素
     * @param value 操作数据
     * @return long
     */
    public Long leftPush(String key, String pivot, String value) {
        Long leftPush = redisTemplate.opsForList().leftPush(key, pivot, value);
        return Optional.ofNullable(leftPush).orElse(0L);
    }

    /**
     * 向左边批量添加参数元素。
     *
     * @param key    list键
     * @param values 值
     * @return long
     */
    public Long leftPushAll(String key, String... values) {
        Long all = redisTemplate.opsForList().leftPushAll(key, values);
        return Optional.ofNullable(all).orElse(0L);
    }

    /**
     * 向集合最右边添加元素。
     *
     * @param key   list键
     * @param value 值
     * @return long
     */
    public Long rightPushAll(String key, String value) {
        Long right = redisTemplate.opsForList().rightPush(key, value);
        return Optional.ofNullable(right).orElse(0L);
    }

    /**
     * 向左边批量添加参数元素。
     *
     * @param key    list键
     * @param values 值
     * @return long
     */
    public Long rightPushAll(String key, String... values) {
        Long rightAll = redisTemplate.opsForList().rightPushAll(key, values);
        return Optional.ofNullable(rightAll).orElse(0L);
    }

    /**
     * 向已存在的集合中添加元素。
     *
     * @param key   list键
     * @param value 值
     * @return long
     */
    public Long rightPushIfPresent(String key, Object value) {
        Long right = redisTemplate.opsForList().rightPushIfPresent(key, value);
        return Optional.ofNullable(right).orElse(0L);
    }

    /**
     * 向已存在的集合中添加元素。
     *
     * @param key list键
     * @return long
     */
    public long listLength(String key) {
        Long size = redisTemplate.opsForList().size(key);
        return Optional.ofNullable(size).orElse(0L);
    }

    /**
     * 移除集合中的左边第一个元素。
     *
     * @param key list键
     */
    public void leftPop(String key) {
        redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 移除集合中左边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
     *
     * @param key list键
     */
    public void leftPop(String key, long timeout, TimeUnit unit) {
        redisTemplate.opsForList().leftPop(key, timeout, unit);
    }

    /**
     * 移除集合中右边的元素。
     *
     * @param key list键
     */
    public void rightPop(String key) {
        redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 移除集合中右边的元素在等待的时间里，如果超过等待的时间仍没有元素则退出。
     *
     * @param key list键
     */
    public void rightPop(String key, long timeout, TimeUnit unit) {
        redisTemplate.opsForList().rightPop(key, timeout, unit);
    }

}
