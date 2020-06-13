package com.ucd.alarm.confirm.scheduletask;

import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.service.AlarmService;
import com.ucd.alarm.confirm.threadtask.AlarmTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ScheduleTask
 * @Description: 告警信息定时任务
 * @Author: liuxin
 * @CreateDate: 2020/6/11 17:38
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Component
public class ScheduleTask {
    @Autowired
    AlarmService alarmService;

    /**
     * @return void
     * @throws
     * @author Crayon
     * @Description 车站id为1的定时任务
     * @date 2020/6/12 4:39 上午
     * @params []
     */
//    @Scheduled(cron = "0/60 * * * * ?")
////    @Async("defaultThreadPool")
//    public void getRedisValueTaskOne() throws Exception {
//        Integer stationId = 1;
//        if (CommandLineRunnerImpl.isDown) {
//            boolean flag = alarmService.updataAlarmLevelResult(stationId);
//        }
//    }
    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwo() throws Exception {

        Integer stationId = BusinessConstants.StationId.DA_HE_GENG_STATION;
        boolean isGetCache = AlarmTaskService.excAlarmResultHashMap.get(stationId) && AlarmTaskService.excRuleResultHashMap.get(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

}