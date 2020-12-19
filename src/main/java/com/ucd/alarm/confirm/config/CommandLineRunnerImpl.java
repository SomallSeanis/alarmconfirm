package com.ucd.alarm.confirm.config;

import com.ucd.alarm.confirm.service.AlarmRealTimeInfosService;
import com.ucd.alarm.confirm.service.AlarmRuleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @ClassName: CommandLineRunnerImpl
 *  * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/8 14:00
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/

/*
    在springBoot项目启动之后,我们需要预加载一些数据

--->权限告警项目中,我们需要预加载的一些数据有: 当程序启动时,
    我们的70个ConcurrentHashMap需要初始化 --> 但是初始化的数据从哪来 --> 从告警表和规则表中来,
          --->因为我们要缓存这2张表的全部信息 -->  然后针对这个map去进行操作.

    我们没有使用Redis框架和KafKa框架 -->  是因为 (太占用资源)

    缓存框架有EhCache 和 MemCache   --> 我们也没用, 因为没有深入了解过, 需要学习成本

    -->所以我们用了程序内缓存 --> static ConcurrentHashMap(之所以用 ConcurrentHashMap是为了在针对ConcurrentHashMap进行操作的时候,保证线程安全.)

 */

@Slf4j
@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    @Autowired
    AlarmRealTimeInfosService alarmRealTimeInfosService;
    @Autowired
    AlarmRuleService alarmRuleService;

    /**todo 修改线程模式为FutureTask，获取线程执行结果，然后执行定时任务*/

    public static Boolean isDown  = false;// 这个就是 当 isDown 为  false 的时候,我的 70 个 ConcurrentHashMap 还没有初始化完成
                                            //定时任务还不能执行!

    @Override
    public void run(String... args) throws Exception {
        //初始化 35个规则信息表的ConcurrentHashMap
        alarmRuleService.getAlarmRuleLists();
        //初始化 35个告警信息表的ConcurrentHashMap
        alarmRealTimeInfosService.getAlarmLists();


        //初始化完成, isDown =true; 这个就是告诉定时任务,可以执行了.
        isDown=true;
        System.out.println("预备资源准备完毕");
    }

}