package com.ucd.alarm.confirm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: TheardConfig
 * @Description: 告警服务线程配置类
 * @Author: liuxin
 * @CreateDate: 2020/6/7 11:49
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Configuration
//启动线程池配置
@EnableAsync
@Slf4j

//我们这就是自定义线程池
public class TheardConfig implements AsyncConfigurer {

    /***
    * @Description: 告警服务使用的线程池    --> 执行告警恢复任务的线程池
    * @param
    * @author  liuxin
    * @return  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
    * @exception
    * @date   2020/6/7 13:11
    */
    @Bean
    //ThreadPoolTaskExecutor ：最常使用，推荐。 其实质是对java.util.concurrent.ThreadPoolExecutor的包装。
    public ThreadPoolTaskExecutor defaultThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目  70
        executor.setCorePoolSize(41);
        //指定最大线程数   100
        executor.setMaxPoolSize(59);
        //队列中最大的数目    35
        executor.setQueueCapacity(18);
        //设置线程优先级
        executor.setThreadPriority(10);
        //线程名称前缀
        executor.setThreadNamePrefix("alarmThreadPool-");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //喜新厌旧策略(丢老的)
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(300);
        //加载
        executor.initialize();
        return executor;
    }


    /**
     * 30分钟---> 执行重新加载数据库数据的线程池 --> 初始化concurrentHashMap的线程池也用他.
     * @return
     */
    @Bean(destroyMethod = "shutdown", name = "reloadDataThreadPool")
    public ThreadPoolTaskExecutor reloadDataThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(18);
        //指定最大线程数
        executor.setMaxPoolSize(36);
        //队列中最大的数目
        executor.setQueueCapacity(18);
        //线程名称前缀
        executor.setThreadNamePrefix("reloadThreadPool-");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //喜新厌旧策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(30);
        //加载
        executor.initialize();
        return executor;
    }

    /***
    * @Description: 定时任务使用的线程池  --->这个线程池只做与定时任务相关的事情 --> 具体执行告警任务的线程 是defaultThreadPool线程池中的线程.
    * @param
    * @author  liuxin
    * @return  org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
    * @exception
    * @date   2020/6/7 13:13
    */
    @Bean(destroyMethod = "shutdown", name = "alarmTaskScheduler")
    //要注意: 定时任务使用的线程池(ThreadPoolTaskScheduler) 和 普通任务使用的线程池(ThreadPoolTaskExecutor) 不一样!
    public ThreadPoolTaskScheduler alarmTaskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        //只管着定时任务的发放,5个定时任务线程足够了
        scheduler.setPoolSize(5);
        scheduler.setThreadNamePrefix("alarmSchedulerTask-");
        scheduler.setAwaitTerminationSeconds(1800);//定时任务线程的最大等待时间30分钟
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        return scheduler;
    }



    @Override
    public Executor getAsyncExecutor() {
        return defaultThreadPool();
    }
    /***
    * @Description: 异步任务中的异常处理
    * @param
    * @author  liuxin
    * @return  org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
    * @exception
    * @date   2020/6/7 13:21
    */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("异步任务执行出现异常, message {}, method {}, params {}", throwable, method, objects);
        };
    }
}
