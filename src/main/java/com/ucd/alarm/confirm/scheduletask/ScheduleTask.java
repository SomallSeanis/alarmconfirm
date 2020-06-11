package com.ucd.alarm.confirm.scheduletask;

import com.ucd.alarm.confirm.config.CommandLineRunnerImpl;
import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.service.AlarmService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import sun.management.snmp.jvminstr.JvmRTInputArgsEntryImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @ClassName: ScheduleTask
 * @Description: TODO
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


    @Scheduled(cron = "0/5 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskOne() throws Exception {
        if (CommandLineRunnerImpl.isDown) {
            //获取告警表Map
            Map<String, List<AlarmRealTimeInfos>> mapByStationId = MemoryCacheUtils.getMapByStationId(1);
            //获取规则表Map
            Map<String, AlarmRule> ruleMapByStationId = MemoryCacheUtils.getRuleMapByStationId(1);
            //获取所有点值key
            List<String> list = mapByStationId.keySet().stream().collect(Collectors.toList());
            //查询点值
            Map<String, Map<String, String>> stringMapMap = alarmService.hashMapListStream(this.getRedisKeyList(), list);
            //获取点值集合
            Map<String, String> map = stringMapMap.get("db0");

        }
    }

    public List<String> getRedisKeyList(){
        List<String> list = new ArrayList<>();
        list.add("db0");
        return list;
    }


}