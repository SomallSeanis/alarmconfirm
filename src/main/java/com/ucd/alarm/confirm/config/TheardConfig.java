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
@EnableAsync
@Slf4j
public class TheardConfig implements AsyncConfigurer {

    /***
    * @Description: 告警服务使用的线程池
    * @param
    * @author  liuxin
    * @return  org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
    * @exception
    * @date   2020/6/7 13:11
    */
    @Bean
    public ThreadPoolTaskExecutor defaultThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(35);
        //指定最大线程数
        executor.setMaxPoolSize(100);
        //队列中最大的数目
        executor.setQueueCapacity(70);
        //线程名称前缀
        executor.setThreadNamePrefix("alarmThreadPool-");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(300);
        //加载
        executor.initialize();
        return executor;
    }

    @Bean(destroyMethod = "shutdown", name = "reloadDataThreadPool")
    public ThreadPoolTaskExecutor reloadDataThreadPool() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数目
        executor.setCorePoolSize(0);
        //指定最大线程数
        executor.setMaxPoolSize(70);
        //队列中最大的数目
        executor.setQueueCapacity(70);
        //线程名称前缀
        executor.setThreadNamePrefix("reloadThreadPool-");
        //rejection-policy：当pool已经达到max size的时候，如何处理新任务
        //CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        //对拒绝task的处理策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //线程空闲后的最大存活时间
        executor.setKeepAliveSeconds(300);
        //加载
        executor.initialize();
        return executor;
    }

    /***
    * @Description: 定时任务使用的线程池
    * @param
    * @author  liuxin
    * @return  org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
    * @exception
    * @date   2020/6/7 13:13
    */
    @Bean(destroyMethod = "shutdown", name = "alarmTaskScheduler")
    public ThreadPoolTaskScheduler alarmTaskScheduler(){
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(36);
        scheduler.setThreadNamePrefix("alarmSchedulerTask-");
        scheduler.setAwaitTerminationSeconds(1800);
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
            log.error("异步任务执行出现异常, message {}, emthod {}, params {}", throwable, method, objects);
        };
    }
}
