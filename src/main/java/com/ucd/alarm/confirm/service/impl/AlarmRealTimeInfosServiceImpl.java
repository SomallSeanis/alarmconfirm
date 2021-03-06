package com.ucd.alarm.confirm.service.impl;

import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.service.AlarmRealTimeInfosService;
import com.ucd.alarm.confirm.threadtask.AlarmTaskService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    public final AlarmTaskService alarmTaskService;

    @Override
    public void getAlarmLists() {
        for (int i = 1; i <= BusinessConstants.STATION_COUNT; i=i+2) {//1,3,5,7....规则表
//            if(i == 32){
//                alarmTaskService.getAlarmListByStationId(BusinessConstants.StationId.DA_YANG_TIAN_CHE_LIANG_DUAN);
//                continue;
//            }
            alarmTaskService.getAlarmListByStationId(i);

        }
    }
}