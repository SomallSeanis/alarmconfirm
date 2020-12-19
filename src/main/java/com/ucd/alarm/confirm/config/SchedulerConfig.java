package com.ucd.alarm.confirm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

/**
 * @ClassName: SchedulerConfig
 * @Description: 告警服务定时任务配置类
 * @Author: liuxin
 * @CreateDate: 2020/6/7 13:02
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Configuration
@EnableScheduling
@Slf4j
//定时任务配置类
public class SchedulerConfig implements SchedulingConfigurer {
    @Autowired
    @Qualifier("alarmTaskScheduler")
    ThreadPoolTaskScheduler taskScheduler; //注入定时任务线程池
    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        scheduledTaskRegistrar.setScheduler(taskScheduler);
        log.info("set spring schedule executor service");
    }
}