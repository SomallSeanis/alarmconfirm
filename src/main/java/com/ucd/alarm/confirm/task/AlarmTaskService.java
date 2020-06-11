package com.ucd.alarm.confirm.task;

import com.ucd.alarm.confirm.constants.AlarmRuleSqlCacheConstants;
import com.ucd.alarm.confirm.constants.AlarmSqlCacheConstants;
import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @ClassName: AlarmTaskService
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/8 12:32
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlarmTaskService {

    @Qualifier("jdbcHikariTemplate")
    private final JdbcTemplate jdbcHikariTemplate;

    @Async("defaultThreadPool")
    public void getAlarmListByStationId(int stationId) {
        //根据站ID查询对应SQL
        String sql = AlarmSqlCacheConstants.getSqlByStationId(stationId);
        Map<String, Object> resultMap = MemoryCacheUtils.getMapByStationId(stationId);
        //根据站ID查询告警
        jdbcHikariTemplate.query(sql, new ResultSetExtractor<List<AlarmRealTimeInfos>>() {
            @Override
            public List<AlarmRealTimeInfos> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<AlarmRealTimeInfos> list = new ArrayList<>();
                while (resultSet.next()) {

                    AlarmRealTimeInfos alarmRealTimeInfos = new AlarmRealTimeInfos();
                    alarmRealTimeInfos.setStationId(resultSet.getInt("StationId"));
                    alarmRealTimeInfos.setPointId(resultSet.getInt("PointId"));
                    alarmRealTimeInfos.setAlarmLevel(resultSet.getInt("AlarmOrder"));
                    alarmRealTimeInfos.setAlarmRuleId(resultSet.getString("AlarmRuleId"));
                    alarmRealTimeInfos.setMaxTime(resultSet.getString("MaxTime"));
                    alarmRealTimeInfos.setSpId(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"));
                    alarmRealTimeInfos.setAlarmSource("SQL");

                    if (resultMap.containsKey(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"))) {
                        List<AlarmRealTimeInfos> tmpList = (List<AlarmRealTimeInfos>) resultMap.get(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"));
                        tmpList.add(alarmRealTimeInfos);
                        resultMap.put(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"), tmpList);
                    } else {
                        List<AlarmRealTimeInfos> alarmList = new ArrayList<>();
                        alarmList.add(alarmRealTimeInfos);
                        resultMap.put(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"), alarmList);
                    }

                }
                return list;
            }
        });

//        System.out.println(mapList);
        //根据站

    }

    @Async("defaultThreadPool")
    public void getAlarmRuleListByStationId(int stationId) {
        String sql = AlarmRuleSqlCacheConstants.getSqlByStationId(stationId);
        Map<String, Object> resultMap = MemoryCacheUtils.getRuleMapByStationId(stationId);
        //根据站ID查询告警
        List<AlarmRule> mapList = jdbcHikariTemplate.query(sql, new ResultSetExtractor<List<AlarmRule>>() {

            @Override
            public List<AlarmRule> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<AlarmRule> list = new ArrayList<>();
                while (resultSet.next()) {
                    AlarmRule alarmRule = new AlarmRule();
                    alarmRule.setId(resultSet.getString("Id"));
                    alarmRule.setStationId(resultSet.getInt("StationId"));
                    alarmRule.setPointId(resultSet.getInt("PointId"));
                    alarmRule.setAlarmOrder(resultSet.getInt("AlarmOrder"));
                    alarmRule.setHighLimit(resultSet.getInt("HighLimit"));
                    alarmRule.setLowLimit(resultSet.getInt("LowLimit"));
                    alarmRule.setAlarmType(resultSet.getInt("AlarmType"));
                    alarmRule.setAlarmSource("SQL");
                    list.add(alarmRule);
                    resultMap.put(resultSet.getString("Id"), alarmRule);
                }
                return list;
            }
        });
    }


}