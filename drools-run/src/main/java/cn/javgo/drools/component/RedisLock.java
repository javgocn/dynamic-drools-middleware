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

    private final StringRedisTemplate redisTemplate;

    private final NativeAsyncTaskExecutePool threadExecutePool;

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
     * 有任务正在执行时，再次请求锁时默认抛出该异常
     */
    private static final Supplier<UtilsException> DEFAULT_LOCK_FAIL_THAN_THROWS = ()
            -> new UtilsException(ExceptionEnum.SYS_REDIS_LOCK_RUNNING_ERROR);

    /**
     * 释放锁 lua 脚本
     */
    private static final String RELEASE_LOCK_LUA_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis"
            + ".call('del', KEYS[1]) else return 0 end";

    /**
     * 加锁
     *
     * @param key      redis 中 key
     * @param value    key 对应的 value
     * @param timeUnit 时间单位
     * @param timeout  锁持有时间
     * @return true加锁成功 false加锁失败
     */
    private boolean lock(String key, String value, TimeUnit timeUnit, long timeout) {
        Boolean result = redisTemplate.opsForValue().setIfAbsent(generateKey(key), value, timeout, timeUnit);
        if (result != null) {
            return result;
        }
        return false;
    }

    /**
     * 解锁：只有指定的 key 存在，且 value 与传入的 value 相等时才会解锁
     *
     * @param key  redis 中 key
     * @param value key 对应的 value
     * @return true解锁成功 false解锁失败
     */
    private boolean unLock(String key, String value) {
        RedisScript<Long> redisScript = new DefaultRedisScript<>(RELEASE_LOCK_LUA_SCRIPT, Long.class);
        Long result = redisTemplate.execute(redisScript, Collections.singletonList(generateKey(key)), value);
        return (result != null && result == 1);
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
     * 组合 key
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
        boolean lock = false;
        try {
            lock = lock(key, value, timeUnit, timeout);
            // 加锁失败
            if (!lock) {
                throw lockFailThanThrows.get();
            }
            return callable.call();
        } catch (Exception e) {
            log.error("加锁运行失败", e);
            throw new UtilsException(ExceptionEnum.SYS_FAILURE_EXCEPTION);
        } finally {
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
            // 加锁
            if (!lock(key, value, timeUnit, timeout)) {
                throw lockFailThanThrows.get();
            }
        } catch (Exception e) {
            log.error("加锁运行失败", e);
            throw new UtilsException(ExceptionEnum.SYS_FAILURE_EXCEPTION);
        }
        // 提交线程异步执行
        Objects.requireNonNull(threadExecutePool.getAsyncExecutor()).execute(() -> {
            try {
                callable.call();
            } catch (Exception e) {
                log.error("加锁执行任务出错", e);
            } finally {
                // 异步执行完才能解锁
                unLock(key, value);
            }
        });
    }
}
