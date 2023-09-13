package cn.javgo.drools.config.thread;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Spring 异步任务线程池配置<br/><br/>
 * 后期优化点：<br/>
 * 1、线程池参数配置化：将线程池的参数（如核心线程数、最大线程数等）配置在配置文件中，使其可以根据不同的环境进行调整，而不是硬编码在代码中。<br/>
 * 2、拒绝策略：当前的拒绝策略是DiscardOldestPolicy，这意味着当队列满时，会丢弃最旧的任务。在某些情况下，我们可能希望记录被丢弃的任务或
 *    采取其他措施。可以考虑自定义拒绝策略。<br/>
 * 3、队列容量：当前的队列容量设置为Integer.MAX_VALUE，这在实际生产环境中可能会导致内存溢出。应该根据实际需求设置一个合理的队列容量。<br/>
 */
@Slf4j
@EnableAsync // 开启 Spring 异步任务支持
@Configuration
public class NativeAsyncTaskExecutePool implements AsyncConfigurer {

    /**
     * 核心线程数（默认线程数）线程池创建时候初始化的线程数
     */
    private static final int CORE_POOL_SIZE = 100;

    /**
     * 最大线程数，只有在缓冲队列满了之后，才会申请超过核心线程数的线程
     */
    private static final int MAXIMUM_POOL_SIZE = 150;

    /**
     * 线程空闲时间（单位：秒）当线程空闲时间达到 KEEP_ALIVE_TIME 时，线程会退出，直到线程数量等于 CORE_POOL_SIZE
     */
    private static final int KEEP_ALIVE_TIME = 60;

    /**
     * 线程名称前缀
     */
    private static final String USER_SPRING_ASYNC_EXECUTOR = "LLI-Spring-Async-Executor";

    /**
     * 重写 AsyncConfigurer 接口的方法，返回一个异步执行的线程池
     *
     * @return Executor 异步任务执行线程池
     */
    @Override
    public Executor getAsyncExecutor() {
        // 创建线程任务执行器
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 初始化线程池
        executor.initialize();;
        // 设置核心线程数
        executor.setCorePoolSize(CORE_POOL_SIZE);
        // 设置最大线程数
        executor.setMaxPoolSize(MAXIMUM_POOL_SIZE);
        // 设置队列容量为 Integer 的最大值
        executor.setQueueCapacity(Integer.MAX_VALUE);
        // 设置线程空闲时间（单位：秒）
        executor.setKeepAliveSeconds(KEEP_ALIVE_TIME);
        // 设置线程名称前缀
        executor.setThreadNamePrefix(USER_SPRING_ASYNC_EXECUTOR);
        // 设置拒绝策略为：抛弃最旧的任务请求。（也就是说，如果当前线程池中的线程数量已经达到最大线程数量，那么后续的任务请求都会被抛弃）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        // 设置线程池关闭的时候等待所有任务都完成再继续销毁其他的 Bean
        executor.setWaitForTasksToCompleteOnShutdown(true);
        // 设置线程池中任务的等待时间（单位：秒），如果超过这个时间还没有销毁就强制销毁，以确保应用最后能够被关闭，而不是阻塞住
        executor.setAwaitTerminationSeconds(60);
        // 返回线程任务执行器
        return executor;
    }

    /**
     * 重写AsyncConfigurer接口的方法，用于处理异步方法执行时的异常
     *
     * @return AsyncUncaughtExceptionHandler 异步任务执行异常处理器
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            public void handleUncaughtException(Throwable ex, Method method, Object... params) {
                // 记录异常信息
                log.error("Async exception method name: " + method.getName(), ex);

                // 发送告警邮件、短信或其他通知
                // notificationService.sendAsyncExceptionAlert(throwable, method.getName());

                // 如果需要，可以进一步处理或记录异常相关的其他信息
                for (Object param : params) {
                    log.error("Parameter value: " + param);
                }
            }
        };
    }
}
