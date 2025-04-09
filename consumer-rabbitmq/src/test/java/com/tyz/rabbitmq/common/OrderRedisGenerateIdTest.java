package com.tyz.rabbitmq.common;


import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@SpringBootTest
public class OrderRedisGenerateIdTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderRedisGenerateIdTest.class);
    @Autowired
    private OrderRedisGenerateId orderRedisGenerateId;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    void generateOrderIdTest() {
        // ForkJoinPool.commonPool 以下代码同一时间有14的并发量 拿到同一时间撮
        for (int ii = 0; ii < 50; ii++) {
            int kk =ii;
            CompletableFuture.runAsync(() -> {
                for (int jj = 0; jj < 20; jj++) {
                    String id = orderRedisGenerateId.generateOrderId();
                    LOGGER.info("generateOrderId 【{}-{}】 ThreadName:{}-{}", kk, jj, Thread.currentThread().getName(), id);
                }
            });
        }
    }

    @Test
    void testRedLock(){
        // 模拟多线程去同时获取一把锁
       //  Redisson分布式锁的实现原理，包括加锁、解锁流程，Lua脚本细节，以及自动续期机制，同时对比了不同分布式锁方案
        LOGGER.info("unlock myLock begin");
        for (int ii = 0; ii < 50; ii++) {
            int kk =ii;
            CompletableFuture.runAsync(() -> {
                RLock lock = redissonClient.getLock("lock:orderId:1");
                // 尝试加锁，等待时间为100秒，锁的持有时间为50秒，如果锁不可用则等待最多100秒，超过时间则
                boolean isLock = false;
                try {
                    isLock = lock.tryLock(100L, 50L, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    // throw new RuntimeException(e);
                }
                if (!isLock) {
                    // 获取锁失败
                    LOGGER.info("tryLock myLock faire:{}【{}】", Thread.currentThread().getName(), kk);
                    return;
                }
                 // 获取锁成功
                try {
                    LOGGER.info("tryLock myLock success:{}【{}】", Thread.currentThread().getName(), kk);
                    TimeUnit.SECONDS.sleep(10L);
                    // 业务逻辑
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException(e);
                } finally {
                    LOGGER.info("unlock myLock success:{}【{}】", Thread.currentThread().getName(), kk);
                    // 释放锁
                    lock.unlock();
                }
            });
        }
        LOGGER.info("unlock myLock end");
        /*RLock lock = redissonClient.getLock("lock:orderId:1");
        boolean isLock = lock.tryLock();
        if (!isLock) {
            LOGGER.info("tryLock myLock faire:{}", Thread.currentThread().getName());
        }
        try {
            LOGGER.info("tryLock myLock success:{}", Thread.currentThread().getName());
            TimeUnit.SECONDS.sleep(2L);
            // 业务逻辑
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }*/
        /*-- 获取锁的Lua脚本*/
       /* -- Redisson 可重入锁获取锁的原理
        local key = KEYS[1]; -- 锁的key
        local threadId = ARGV[1]; -- 线程唯一标识
        local releaseTime = ARGV[2]; -- 锁的自动释放时间
                -- 判断是否存在
        if(redis.call('exists', key) == 0) then
                -- 不存在, 获取锁
        redis.call('hset', key, threadId, '1');
        -- 设置有效期
        redis.call('expire', key, releaseTime);
        return 1; -- 返回结果
        end;
        -- 锁已经存在，判断threadId是否是自己
        if(redis.call('hexists', key, threadId) == 1) then
                -- 存在, 获取锁，重入次数+1
        redis.call('hincrby', key, threadId, '1');
        -- 设置有效期
        redis.call('expire', key, releaseTime);
        return 1; -- 返回结果
        end;
        return 0; -- 代码走到这里,说明获取锁的不是自己，获取锁失败*/

        /*-- 释放锁的Lua脚本*/
       /* -- Redisson可重入锁的删除锁原理
        local key = KEYS[1]; -- 锁的key
        local threadId = ARGV[1]; -- 线程唯一标识
        local releaseTime = ARGV[2]; -- 锁的自动释放时间
        -- 判断当前锁是否还是被自己持有
        if (redis.call('HEXISTS', key, threadId) == 0) then
        return nil; -- 如果已经不是自己，则直接返回
                end;
        -- 是自己的锁，则重入次数-1
        local count = redis.call('HINCRBY', key, threadId, -1);
        -- 判断是否重入次数是否已经为0
        if (count > 0) then
        -- 大于0说明不能释放锁，重置有效期然后返回
        redis.call('EXPIRE', key, releaseTime);
        return nil;
        else  -- 等于0说明可以释放锁，直接删除
        redis.call('DEL', key);
        return nil;
        end;*/



    }
}