package com.ucd.alarm.confirm.task;

import com.ucd.alarm.confirm.constants.SqlCacheConstants;
import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
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
        String sql = SqlCacheConstants.getSqlByStationId(stationId);
        //根据站ID查询告警
        List<AlarmRealTimeInfos> mapList = jdbcHikariTemplate.query(sql, new ResultSetExtractor<List<AlarmRealTimeInfos>>() {

            @Override
            public List<AlarmRealTimeInfos> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
                List<AlarmRealTimeInfos> list = new ArrayList<>();
                while (resultSet.next()) {
                    AlarmRealTimeInfos alarmRealTimeInfos =new AlarmRealTimeInfos();
                    alarmRealTimeInfos.setStationId(resultSet.getInt("StationId"));
                    alarmRealTimeInfos.setPointId(resultSet.getInt("PointId"));
                    alarmRealTimeInfos.setAlarmLevel(resultSet.getInt("AlarmLevel"));
                    alarmRealTimeInfos.setAlarmRuleId(resultSet.getString("AlarmRuleId"));
                    alarmRealTimeInfos.setMaxTime(resultSet.getString("MaxTime"));
                    alarmRealTimeInfos.setSpId(resultSet.getInt("StationId")+"_"+resultSet.getInt("PointId"));
                    alarmRealTimeInfos.setAlarmSource("SQL");
                    list.add(alarmRealTimeInfos);
                }
                return list;
            }
        });

//        System.out.println(mapList);
        //根据站

    }



}