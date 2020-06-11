package com.ucd.alarm.confirm.config;

import com.ucd.alarm.confirm.service.AlarmRealTimeInfosService;
import com.ucd.alarm.confirm.service.AlarmRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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

    /**todo 修改线程模式为FutureTask，获取线程执行结果，然后执行定时任务*/
    public static Boolean isDown  = false;

    @Override
    public void run(String... args) throws Exception {
        alarmRealTimeInfosService.getAlarmLists();
        alarmRuleService.getAlarmRuleLists();
        isDown=true;
    }

}