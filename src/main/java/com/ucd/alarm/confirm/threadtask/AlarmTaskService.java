package com.ucd.alarm.confirm.threadtask;

import com.ucd.alarm.confirm.constants.AlarmRuleSqlCacheConstants;
import com.ucd.alarm.confirm.constants.AlarmSqlCacheConstants;
import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @ClassName: AlarmTaskService
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/8 12:32
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlarmTaskService {

  //  public static final Map<Integer, Boolean> excAlarmResultHashMap = new ConcurrentHashMap<>();
  //  public static final Map<Integer, Boolean> excRuleResultHashMap = new ConcurrentHashMap<>();
    public static final Map<Integer, Boolean> excAlarmResultHashMap = new HashMap<>();
    public static final Map<Integer, Boolean> excRuleResultHashMap = new HashMap<>();
    @Qualifier("jdbcHikariTemplate")
    private final JdbcTemplate jdbcHikariTemplate;

    @Async("reloadDataThreadPool")
    public void getAlarmListByStationId(int stationId) {
        excAlarmResultHashMap.put(stationId, false);

        //根据站ID查询对应SQL
        String sql = AlarmSqlCacheConstants.getSqlByStationId(stationId);
        //这就是那个车站对应的用于存储信息的ConcurrentHashMap
        Map<String, List<AlarmRealTimeInfos>> resultMap = MemoryCacheUtils.getMapByStationId(stationId);
        resultMap.clear();
        log.info("alarmstation{},alarmmapsize{}",stationId,resultMap.size());
        //根据站ID查询告警
        jdbcHikariTemplate.query(sql, new ResultSetExtractor<List<AlarmRealTimeInfos>>() {
            @Override
            public List<AlarmRealTimeInfos> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<AlarmRealTimeInfos> list = new ArrayList<>(); //这个list没用, 完全是为了有个返回值.
                while (resultSet.next()) {

                    AlarmRealTimeInfos alarmRealTimeInfos = new AlarmRealTimeInfos();
                    alarmRealTimeInfos.setStationId(resultSet.getInt("StationId"));
                    alarmRealTimeInfos.setPointId(resultSet.getInt("PointId"));
                    alarmRealTimeInfos.setAlarmOrder(resultSet.getInt("AlarmOrder"));
                    alarmRealTimeInfos.setAlarmRuleId(resultSet.getString("AlarmRuleId"));
                    alarmRealTimeInfos.setMaxTime(resultSet.getString("MaxTime"));
                    alarmRealTimeInfos.setAlarmType(resultSet.getInt("AlarmType"));
                    alarmRealTimeInfos.setSpId(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"));
                    alarmRealTimeInfos.setAlarmSource("SQL");

                    if (resultMap.containsKey(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"))) {//先判断ConcurrentHashMap中有无这个键.
                        List<AlarmRealTimeInfos> tmpList = resultMap.get(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId")); //把这个键的值取出来.
                        tmpList.add(alarmRealTimeInfos);//添加上新的一条
                        resultMap.put(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"), tmpList);//这个逻辑是正确的
                    } else {
                        List<AlarmRealTimeInfos> alarmList = new ArrayList<>();
                        alarmList.add(alarmRealTimeInfos);
                        resultMap.put(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"), alarmList);
                    }
                }
                log.info("alarmrealtimeinfos表查询结果alarmstation{},alarmmapsize{}",stationId,resultMap.size());
                return list;
            }
        });
        excAlarmResultHashMap.put(stationId, true);

    }

    @Async("reloadDataThreadPool")
    public void getAlarmRuleListByStationId(int stationId) {
        excRuleResultHashMap.put(stationId, false);
        String sql = AlarmRuleSqlCacheConstants.getSqlByStationId(stationId);
        Map<String, List<AlarmRule>> resultMap = MemoryCacheUtils.getRuleMapByStationId(stationId);
        resultMap.clear();
        log.info("rulestation{},rulemapsize{}",stationId,resultMap.size());
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
                    if (resultMap.containsKey(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"))) {
                        List<AlarmRule> tmpList = resultMap.get(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"));
                        tmpList.add(alarmRule);
                        resultMap.put(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"), tmpList);
                    } else {
                        List<AlarmRule> alarmList = new ArrayList<>();
                        alarmList.add(alarmRule);
                        resultMap.put(resultSet.getInt("StationId") + "_" + resultSet.getInt("PointId"), alarmList);
                    }
                }
                log.info("rule表查询结果rulestation{},rulemapsize{}",stationId,resultMap.size());
                return list;
            }
        });
        excRuleResultHashMap.put(stationId, true);
    }


}