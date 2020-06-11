package com.ucd.alarm.confirm.scheduletask;

import com.ucd.alarm.confirm.config.CommandLineRunnerImpl;
import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.service.AlarmService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
     * @author Crayon
     * @Description  车站id为1的定时任务
     * @date 2020/6/12 4:39 上午
     * @params []
     * @exception
     * @return void
     */
    @Scheduled(cron = "0/5 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskOne() throws Exception {
        Integer stationId = 1;
        if (CommandLineRunnerImpl.isDown) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

}