package com.ucd.alarm.confirm.service.impl;

import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.entity.StationAlarmSyncEntity;
import com.ucd.alarm.confirm.service.AlarmRuleService;
import com.ucd.alarm.confirm.threadtask.AlarmTaskService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
//import org.apache.kafka.common.protocol.types.Field;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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


    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Qualifier("jdbcHikariTemplate")
    private final JdbcTemplate jdbcHikariTemplate;

    public final AlarmTaskService alarmTaskService;

    private final Environment environment;

    public static final DateTimeFormatter SS_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSSS");

    @Override
    public void getAlarmRuleLists() {
        for (int i = 1; i <= BusinessConstants.STATION_COUNT; i=i+2) { //1,3,5,7
            //将station ID为32替换为39
//            if(i == 32){
//                alarmTaskService.getAlarmRuleListByStationId(BusinessConstants.StationId.DA_YANG_TIAN_CHE_LIANG_DUAN);
//                continue;
//            }
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
        //如果告警类型是超限告警类型
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
                doUpdata(stationId, alarmType, pointId, time, order);
            }
        }
        //如果告警类型不是超限告警类型
        if (alarmType != BusinessConstants.ALARM_LIMIT_TYPE) {
            for (int i = 0; i < alarmRuleList.size(); i++) {
                if (i < 1) {
                    float highLimit = alarmRuleList.get(i).getHighLimit();
                    if (value != highLimit) {
                        //执行自动确认sql
                        doUpdata(stationId, alarmType, pointId, time, alarmOrder);
                    }
                }

            }
        }
        return isUpdata;
    }

    private void doUpdata(int stationId, int alarmType, int pointId, String time, int order) throws DataAccessException {
        List<Map<String, Object>> queryForList = null;
        //获取当前时间
        String nowtime = SS_FORMATTER.format(LocalDateTime.now(ZoneId.of("Asia/Shanghai"))) + " +08:00";
        String sql = "update AlarmRealTimeInfoes set AlarmStatus =1 from AlarmRealTimeInfoes as A INNER join AlarmRule as B on A.AlarmRuleId =B.EntityId left join AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where A.StationId=" + stationId + " and A.PointId= " + pointId + " and B.alarmType=" + alarmType + " and A.AlarmStatus=0 and C.[Order]<= " + order + " and A.AlarmDateTime<=" + time;
        //这个不加的话 sqlServer会报错 --> 产生死锁问题
        log.info("没有进synchronized,updateAlarmrule,stationId = " + stationId + ", alarmType = " + alarmType + ", pointId = " + pointId + ", time = " + time + ", order = " + order);
        String selectSql = "select Id from AlarmRealTimeInfoes as A INNER join AlarmRule as B on A.AlarmRuleId =B.EntityId left join AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where A.StationId=" + stationId + " and A.PointId= " + pointId + " and B.alarmType=" + alarmType + " and A.AlarmStatus=0 and C.[Order]<= " + order + " and A.AlarmDateTime<=" + time;
        synchronized (this) {
            int update = -1;
            //解析ID
            long queryStart = System.currentTimeMillis();
            queryForList = jdbcHikariTemplate.queryForList(selectSql);
            long queryEnd = System.currentTimeMillis();
            log.info(Thread.currentThread().getName()+"执行查询用的时间是"+(queryEnd - queryStart)+"ms"+"这个线程要复归的站是"+stationId+"要复归的点是"+pointId+"并且他查出来的记录条数为"+queryForList.size());
            if(queryForList != null && queryForList.size()!=0){
                long start = System.currentTimeMillis();
                update = jdbcHikariTemplate.update(sql);
                long end = System.currentTimeMillis();
                log.info(Thread.currentThread().getName()+"执行update用的时间是"+(end - start)+"ms"+"这个线程要复归的站是"+stationId+"要复归的点是"+pointId+"并且他影响记录的行数是"+update);
            }
        }
        if(queryForList==null ||queryForList.size()==0){return;}
        String insertSql = "insert into AlarmComments (CommentTime,Person,AlarmRealTimeInfo_Id,CommentDetail) VALUES ";
        for (int i = 0; i < queryForList.size(); i++) {
            String id = String.valueOf(queryForList.get(i).get("Id"));
           // log.info("插入alarmComments的站有"+queryForList.get(i).get("StationId")+"复归成功的点是"+queryForList.get(i).get("PointId"));
            if (i == queryForList.size() - 1) {
                insertSql += "('" + nowtime + "','AlarmReset','" + id + "','告警复归');";
                break;
            }
            insertSql += "('" + nowtime + "','AlarmReset','" + id + "','告警复归'),";
        }

        //插入AlarmComments
        jdbcHikariTemplate.execute(insertSql);
        log.info("在中心AlarmComments表中插入的sql是"+insertSql);
        StationAlarmSyncEntity stationAlarmSyncEntity = new StationAlarmSyncEntity();
        stationAlarmSyncEntity.setStationId(stationId);
        stationAlarmSyncEntity.setUpdateSql(sql);
        stationAlarmSyncEntity.setInsertSql(insertSql);
        kafkaTemplate.send("StationAlarmSyncTopic",stationAlarmSyncEntity);

        Map<String, List<AlarmRealTimeInfos>> mapByStationId = MemoryCacheUtils.getMapByStationId(stationId);
        if (!ObjectUtils.isEmpty(mapByStationId) || mapByStationId.size() != 0) {
            mapByStationId.remove(stationId + "_" + pointId);
        }
    }
}