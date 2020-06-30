package com.ucd.alarm.confirm.service.impl;

import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.service.AlarmRuleService;
import com.ucd.alarm.confirm.threadtask.AlarmTaskService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

    @Qualifier("jdbcHikariTemplate")
    private final JdbcTemplate jdbcHikariTemplate;

    public final AlarmTaskService alarmTaskService;

    @Override
    public void getAlarmRuleLists() {
        for (int i = 1; i <= BusinessConstants.STATION_COUNT; i++) {
            alarmTaskService.getAlarmRuleListByStationId(i);
        }
    }


    @Override
    public boolean doRuleCheck(int stationId, String redisValue, int alarmOrder, int alarmType, String maxTime, List<AlarmRule> alarmRuleList) {
        float value = 0;
        boolean isUpdata = true;
        if (!StringUtils.isEmpty(redisValue)) {
            value = Float.parseFloat(redisValue);
        }
        if (ObjectUtils.isEmpty(alarmRuleList) || alarmRuleList.size() == 0) {
            return false;
        }
        int pointId = alarmRuleList.get(0).getPointId();
        String time = "\'" + maxTime + "\';";
        if (alarmType == BusinessConstants.ALARM_LIMIT_TYPE) {
            float finalValue = value;
            List<AlarmRule> orderList = alarmRuleList.stream().filter(alarmRule -> (finalValue <= alarmRule.getHighLimit() && finalValue >= alarmRule.getLowLimit()))
                    .collect(Collectors.toList());
            if (ObjectUtils.isEmpty(orderList) || orderList.size() == 0) {
                return false;
            }
            Integer order = orderList.stream().map(alarmRule -> alarmRule.getAlarmOrder()).max(Integer::compareTo).get();
            //判断是否告警等级变小
            if (order >= alarmOrder) {
                //执行自动确认sql
                doUpdata(stationId, alarmType, pointId, time,order);
            }
        }
        if (alarmType != BusinessConstants.ALARM_LIMIT_TYPE) {
            for (int i = 0; i < alarmRuleList.size(); i++) {
                if (i < 1) {
                    float highLimit = alarmRuleList.get(i).getHighLimit();
                    if (value != highLimit) {
                        //执行自动确认sql
                        doUpdata(stationId, alarmType, pointId, time,alarmOrder);
                    }
                }

            }
        }
        return isUpdata;
    }

    private void doUpdata(int stationId, int alarmType, int pointId, String time,int order) throws DataAccessException {
        String sql = "update AlarmRealTimeInfoes set AlarmStatus =1 from AlarmRealTimeInfoes as A INNER join AlarmRule as B on A.AlarmRuleId =B.EntityId left join AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where A.StationId=" + stationId + " and A.PointId= " + pointId + " and B.alarmType=" + alarmType + " and A.AlarmStatus=0 and C.[Order]<= " + order + " and A.AlarmDateTime<=" + time;
        //这个不加的话 sqlServer会报错 --> 产生死锁问题
        log.info("updateAlarmrule,stationId = " + stationId + ", alarmType = " + alarmType + ", pointId = " + pointId + ", time = " + time + ", order = " + order);;

//        synchronized (this) {
//           // log.info("updateAlarmrule,stationId = " + stationId + ", alarmType = " + alarmType + ", pointId = " + pointId + ", time = " + time + ", order = " + order);;
//          //  jdbcHikariTemplate.update(sql);
//        }
        Map<String, List<AlarmRealTimeInfos>> mapByStationId = MemoryCacheUtils.getMapByStationId(stationId);
        if (!ObjectUtils.isEmpty(mapByStationId) || mapByStationId.size() != 0) {
            mapByStationId.remove(stationId + "_" + pointId);
        }
    }
}