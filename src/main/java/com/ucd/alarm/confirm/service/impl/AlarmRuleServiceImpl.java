package com.ucd.alarm.confirm.service.impl;

import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.service.AlarmRuleService;
import com.ucd.alarm.confirm.threadtask.AlarmTaskService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

    @Qualifier("jdbcHikariTemplate")
    private final JdbcTemplate jdbcHikariTemplate;

    public final AlarmTaskService alarmTaskService;

    @Override
    public List<Map<String, Object>> getAlarmRuleLists() throws InterruptedException {
        for (int i = 1; i<= BusinessConstants.STATION_COUNT; i++){
            alarmTaskService.getAlarmRuleListByStationId(i);
        }
        Thread.sleep(10000L);
        for (int i = 1; i<= BusinessConstants.STATION_COUNT; i++) {
            System.out.println("RuleMap"+i+":"+MemoryCacheUtils.getRuleDataSize(MemoryCacheUtils.getRuleMapByStationId(i)));
        }
        return null;
    }


    @Override
    public void doRuleCheck(int stationId, String redisValue, int alarmOrder, int alarmType, String maxTime, List<AlarmRule> alarmRuleList) {
        float value = 0;
        if (!StringUtils.isEmpty(redisValue)) {
            value = Float.parseFloat(redisValue);
        }
        int pointId = alarmRuleList.get(0).getPointId();
        String time = "\'" + maxTime + "\';";
        if (alarmType == BusinessConstants.ALARM_LIMIT_TYPE) {
            float finalValue = value;
            Integer order = alarmRuleList.stream().filter(alarmRule -> (finalValue > alarmRule.getHighLimit() && finalValue < alarmRule.getLowLimit()))
                    .map(alarmRule -> alarmRule.getAlarmOrder()).max(Integer::compareTo).get();
            //判断是否告警等级变小
            if (order > alarmOrder) {
                //执行自动确认sql
                doUpdata(stationId, alarmType, pointId, time);
            }
        }
        if (alarmType != BusinessConstants.ALARM_LIMIT_TYPE) {
            for (int i = 1; i < alarmRuleList.size(); i++) {
                if (i < 2) {
                    float highLimit = alarmRuleList.get(i).getHighLimit();
                    if (value != highLimit) {
                        //执行自动确认sql
                        doUpdata(stationId, alarmType, pointId, time);
                    }
                }

            }
        }
    }
    private void doUpdata(int stationId, int alarmType, int pointId, String time) {
        String sql = "update AlarmRealTimeInfoes set AlarmStatus =1 where StationId=" + stationId + " and PointId= " + pointId + " and alarmType=" + alarmType + " and AlarmStatus=0 and AlarmDateTime<=" + time;
        jdbcHikariTemplate.update(sql);
    }
}