package com.ucd.alarm.confirm.config;

import com.ucd.alarm.confirm.service.AlarmRealTimeInfosService;
import com.ucd.alarm.confirm.service.AlarmRuleService;
import com.ucd.alarm.confirm.task.AlarmTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @ClassName: CommandLineRunnerImpl
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/8 14:00
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    AlarmRealTimeInfosService alarmRealTimeInfosService;
    @Autowired
    AlarmRuleService alarmRuleService;

    @Override
    @Async("defaultThreadPool")
    public void run(String... args) throws Exception {
        alarmRealTimeInfosService.getAlarmLists();
        alarmRuleService.getAlarmRuleLists();
    }

}