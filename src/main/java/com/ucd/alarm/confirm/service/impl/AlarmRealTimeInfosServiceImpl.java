package com.ucd.alarm.confirm.service.impl;

import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.service.AlarmRealTimeInfosService;
import com.ucd.alarm.confirm.task.AlarmTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AlarmRealTimeInfosServiceImpl
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/7 15:53
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlarmRealTimeInfosServiceImpl implements AlarmRealTimeInfosService {

    public  final  AlarmTaskService alarmTaskService;


    @Override
    public List<Map<String, Object>> getAlarmLists() {
        for (int i = 1; i<= BusinessConstants.STATION_COUNT; i++){
            alarmTaskService.getAlarmListByStationId(i);
        }
        return null;
    }
}