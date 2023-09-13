package cn.javgo.drools.component;

import cn.javgo.drools.config.thread.NativeAsyncTaskExecutePool;
import cn.javgo.drools.exception.UtilsException;
import cn.javgo.drools.exception.enums.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Redis 分布式锁
 */
@Slf4j
@Component
public class RedisLock {

    /**
     * Redis 操作模板
     */
    private final StringRedisTemplate redisTemplate;

    /**
     * Spring 异步任务执行线程池
     */
    private final NativeAsyncTaskExecutePool threadExecutePool;

    /**
     * 构造器注入
     */
    @Autowired
    public RedisLock(StringRedisTemplate redisTemplate, NativeAsyncTaskExecutePool threadExecutePool) {
        this.redisTemplate = redisTemplate;
        this.threadExecutePool = threadExecutePool;
    }

    /**
     * Redis 分布式锁前缀
     */
    private static final String REDIS_LOCK_PREFIX = "redis_lock_";

    /**
     * Redis 分布式锁默认异常：当获取锁失败时，抛出此异常 “存在进行中的任务，请稍后再试”
     */
    private static final Supplier<UtilsException> DEFAULT_LOCK_FAIL_THAN_THROWS = ()
            -> new UtilsException(ExceptionEnum.SYS_REDIS_LOCK_RUNNING_ERROR);

    /**
     * 释放分布式锁的 Lua 脚本（原子操作）
     * if redis.call('get', KEYS[1]) == ARGV[1] then // 获取分布式锁的 key 对应的 value，判断是否与传入的 value 相等（比较锁的唯一标识）
     *      return redis.call('del', KEYS[1])   // 相等则删除锁，返回 1
     * else <br/>
     *      return 0    // 不相等则返回 0
     * end
     */
    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis"
            + ".call('del', KEYS[1]) else return 0 end";

    /**
     * 加分布式锁
     *
     * @param key      redis 中 key
     * @param value    key 对应的 value
     * @param timeUnit 时间单位
     * @param timeout  锁持有时间
     * @return true加锁成功 false加锁失败
     */
    private boolean lock(String key, String value, TimeUnit timeUnit, long timeout) {
        // SETNX key value：只有在 key 不存在时设置 key 的值为 value，若 key 已经存在，则不做任何操作
        Boolean result = redisTemplate.opsForValue().setIfAbsent(generateKey(key), value, timeout, timeUnit);

        // 若 key 不存在，则设置成功，加锁成功
        if (result != null) {
            return result;
        }

        // 若 key 已经存在，则不做任何操作，加锁失败
        return false;
    }

    /**
     * 释放锁
     *
     * @param key  redis 中 key
     * @param value key 对应的 value
     * @return true解锁成功 false解锁失败
     */
    private boolean unLock(String key, String value) {
        // 使用 Lua 脚本保证原子性
        RedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT, Long.class);

        // 执行 Lua 脚本，释放锁（返回 1 表示释放锁成功，返回 0 表示释放锁失败）
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(generateKey(key)), value);

        // 返回释放锁结果
        return (result != null && result == 1);
    }

    /**
     * 组合 Redis 分布式锁的 key
     *
     * @param key redis 中 key
     * @return 组合后的 key
     */
    private static String generateKey(String key) {
        return REDIS_LOCK_PREFIX + key;
    }

    /**
     * 在锁内执行任务（同步调用）
     *
     * @param key       redis 中 key
     * @param value     key 对应的 value
     * @param timeUnit  时间单位
     * @param timeout   锁持有时间
     * @param callable  任务
     * @return          任务执行结果
     * @param <T>       任务执行结果类型
     */
    public <T> T runWithLockSync(String key, String value, TimeUnit timeUnit, long timeout, Callable<T> callable) {
        return doRunWithLockSync(key, value, timeUnit, timeout, callable, DEFAULT_LOCK_FAIL_THAN_THROWS);
    }

    /**
     * 在锁内执行任务（同步调用）
     *
     * @param key                   redis 中 key
     * @param value                 key 对应的 value
     * @param timeUnit              时间单位
     * @param timeout               锁持有时间
     * @param callable              任务
     * @param lockFailThanThrows    加锁失败时抛出的异常
     * @return                      任务执行结果
     * @param <T>                   任务执行结果类型
     */
    private <T> T doRunWithLockSync(String key, String value, TimeUnit timeUnit, long timeout, Callable<T> callable,
                                    Supplier<UtilsException> lockFailThanThrows) {
        // 默认没有加锁
        boolean lock = false;

        try {
            // 尝试加锁
            lock = lock(key, value, timeUnit, timeout);

            // 如果加锁失败，则抛出异常
            if (!lock) {
                throw lockFailThanThrows.get();
            }

            // 加锁成功，则执行任务
            return callable.call();
        } catch (Exception e) {
            log.error("加锁运行失败", e);
            throw new UtilsException(ExceptionEnum.SYS_FAILURE_EXCEPTION);
        } finally {
            // 如果加锁成功，则释放锁
            if (lock) {
                this.unLock(key, value);
            }
        }
    }

    /**
     * 在锁内执行任务（异步调用）
     *
     * @param key                   redis 中 key
     * @param value                 key 对应的 value
     * @param timeUnit              时间单位
     * @param timeout               锁持有时间
     * @param callable              任务
     * @param lockFailThanThrows    加锁失败时抛出的异常
     */
    public void runWithLockAsync(String key, String value, TimeUnit timeUnit, long timeout, Callable<?> callable,
                                 Supplier<UtilsException> lockFailThanThrows) {
        doRunWithLockAsync(key, value, timeUnit, timeout, callable, lockFailThanThrows);
    }

    /**
     * 在锁内执行任务（异步调用）
     *
     * @param key                   redis 中 key
     * @param value                 key 对应的 value
     * @param timeUnit              时间单位
     * @param timeout               锁持有时间
     * @param callable              任务
     * @param lockFailThanThrows    加锁失败时抛出的异常
     */
    private void doRunWithLockAsync(String key, String value, TimeUnit timeUnit, long timeout, Callable<?> callable,
                                    Supplier<UtilsException> lockFailThanThrows) {
        try {
            // 尝试加锁，如果加锁失败，则抛出异常
            if (!lock(key, value, timeUnit, timeout)) {
                throw lockFailThanThrows.get();
            }
        } catch (Exception e) {
            log.error("加锁运行失败", e);
            throw new UtilsException(ExceptionEnum.SYS_FAILURE_EXCEPTION);
        }

        // 提交线程异步执行：从线程池中获取线程执行器，执行任务
        Objects.requireNonNull(threadExecutePool.getAsyncExecutor()).execute(() -> {
            try {
                // 执行任务
                callable.call();
            } catch (Exception e) {
                log.error("加锁执行任务出错", e);
            } finally {
                // 异步执行完毕，释放锁
                unLock(key, value);
            }
        });
    }
}
