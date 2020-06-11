package com.ucd.alarm.confirm.service.impl;

import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.service.AlarmRuleService;
import com.ucd.alarm.confirm.task.AlarmTaskService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AlarmRuleServiceImpl
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/7 15:56
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlarmRuleServiceImpl implements AlarmRuleService {

    public  final AlarmTaskService alarmTaskService;

    @Override
    public List<Map<String, Object>> getAlarmRuleLists() throws InterruptedException {
        for (int i = 1; i<= BusinessConstants.STATION_COUNT; i++){
            alarmTaskService.getAlarmRuleListByStationId(i);
        }
        Thread.sleep(20000L);
        for (int i = 1; i<= BusinessConstants.STATION_COUNT; i++) {
            Thread.sleep(10000L);
            System.out.println("RuleMap"+i+":"+MemoryCacheUtils.getDataSize(MemoryCacheUtils.getRuleMapByStationId(i)));
        }
        return null;
    }
}