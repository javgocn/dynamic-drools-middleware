package cn.javgo.drools.support.factory;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import static java.util.concurrent.TimeUnit.MINUTES;

/**
 * 自定义线程池工厂类
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE) // 私有构造函数，确保该类不能被实例化，只能调用其静态方法
public class ThreadPoolExecutorFactory {

    //================================================ 线程池 ================================================//

    /**
     * 线程池核心线程数
     */
    private static final int CORE_POOL_SIZE = 100;

    /**
     * 线程池最大线程数
     */
    private static final int MAXIMUM_POOL_SIZE = 150;

    /**
     * 线程空闲时间（单位：秒）当线程空闲时间达到 KEEP_ALIVE_TIME 时，线程会退出，直到线程数量等于 CORE_POOL_SIZE
     */
    private static final int KEEP_ALIVE_TIME = 60;

    /**
     * 线程池对象
     */
    private static ThreadPoolExecutor threadPoolExecutor = null;

    //=========================================== Spring 异步任务线程池 ===========================================//

    /**
     * Spring 异步任务线程池对象
     */
    private static ThreadPoolTaskExecutor springAsyncThreadPoolExecutor = null;

    /**
     * Spring 异步任务线程池核心线程数
     */
    private static final int SPRING_ASYNC_CORE_POOL_SIZE = 100;

    /**
     * Spring 异步任务线程池最大线程数
     */
    private static final int SPRING_ASYNC_MAXIMUM_POOL_SIZE = 150;

    /**
     * Spring 异步任务线程池前缀
     */
    public static final String USER_SPRING_ASYNC_EXECUTOR = "My-Spring-Async-Executor";

    //========================================== 获取线程池对象（单例模式-双重检查锁）===================================//

    /**
     * 获取 ThreadPoolExecutor 线程池对象（双重检查锁定）<br/><br/>
     * 说明：双重检查锁定（double-checked locking）是一种使用同步块加锁的方法。程序员称其为双重检查锁，因为会有两次检查 instance == null，
     *      一次是在同步块外，一次是在同步块内。为什么在同步块内还要再检验一次？因为可能会有多个线程一起进入同步块外的 if，如果在同步块内
     *      不进行二次 instance == null 检验的话就会生成多个实例了。从而违反了单例模式的初衷：一个类只有一个实例。
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(){
        // 同步块外进行第一次检查（此处可能有多个线程一起进入）
        if (threadPoolExecutor == null) {

            ThreadPoolExecutor t;

            // 锁定 ThreadPoolExecutorFactory 类，确保同一时刻只有一个线程能够进入同步块
            synchronized (ThreadPoolExecutorFactory.class) {

                // 临时变量 t 指向 ThreadPoolExecutor 实例
                t = threadPoolExecutor;

                // 同步块内进行第二次检查（此处只能有一个线程进入）
                if (t == null){
                    // 为 ThreadPoolExecutor 上锁，防止多线程下创建多个实例
                    synchronized (ThreadPoolExecutor.class){
                        t = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, MINUTES,
                                new LinkedBlockingQueue<>(),new ThreadPoolExecutor.DiscardOldestPolicy());
                    }
                    threadPoolExecutor = t;
                }
            }
        }

        // 返回 ThreadPoolExecutor 实例（单例）
        return threadPoolExecutor;
    }

    /**
     * 获取 Spring 异步任务线程池对象（双重检查锁定）
     */
    public static ThreadPoolTaskExecutor getSpringAsyncThreadPoolExecutor(){
        // 同步块外进行第一次检查（此处可能有多个线程一起进入）
        if (null == springAsyncThreadPoolExecutor) {
            ThreadPoolTaskExecutor t;

            // 锁定 ThreadPoolTaskExecutorFactory 类，确保同一时刻只有一个线程能够进入同步块
            synchronized (ThreadPoolTaskExecutor.class) {

                // 临时变量 t 指向 ThreadPoolTaskExecutor 实例
                t = springAsyncThreadPoolExecutor;

                // 同步块内进行第二次检查（此处只能有一个线程进入）
                if (null == t) {
                    // 为 ThreadPoolTaskExecutor 上锁，防止多线程下创建多个实例
                    synchronized (ThreadPoolTaskExecutor.class) {
                        t = new ThreadPoolTaskExecutor();
                        t.initialize();
                        t.setCorePoolSize(SPRING_ASYNC_CORE_POOL_SIZE);
                        t.setQueueCapacity(Integer.MAX_VALUE);
                        t.setThreadNamePrefix(USER_SPRING_ASYNC_EXECUTOR);
                        t.setMaxPoolSize(SPRING_ASYNC_MAXIMUM_POOL_SIZE);
                        t.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
                        t.setWaitForTasksToCompleteOnShutdown(true);
                        t.setAwaitTerminationSeconds(60);
                    }

                    // 将临时变量 t 指向的 ThreadPoolTaskExecutor 实例赋值给静态成员变量 springAsyncThreadPoolExecutor
                    springAsyncThreadPoolExecutor = t;
                }
            }
        }

        // 返回 ThreadPoolTaskExecutor 实例（单例）
        return springAsyncThreadPoolExecutor;
    }
}
