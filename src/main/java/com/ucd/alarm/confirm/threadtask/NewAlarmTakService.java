//package com.ucd.alarm.confirm.threadtask;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//
//import java.util.HashMap;
//import java.util.Map;
///**
// * 单个线程去查询数据库并处理，--> IO密集型任务转CPU密集型任务
// */
//@Slf4j
//@Component
//@RequiredArgsConstructor(onConstructor = @__(@Autowired))
//public class NewAlarmTakService {
//    public static final Map<Integer, Boolean> excAlarmResultHashMap = new HashMap<>();
//    public static final Map<Integer, Boolean> excRuleResultHashMap = new HashMap<>();
//    @Qualifier("jdbcHikariTemplate")
//    private final JdbcTemplate jdbcHikariTemplate;
//
//    //
//
//    //查告警信息的sql --> 一下把所有的站都查出来,--> 在代码里面做站的区分
//    String alarmSql = "select A.StationId, A.PointId, A.AlarmRuleId, C.[Order] as AlarmOrder, D.AlarmType as AlarmType , B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 1 group by StationId,PointId)B on A.StationId=B.StationId AND A.PointId=B.PointId AND  A.AlarmDateTime=B.MaxTime LEFT JOIN AlarmLevels as C on C.EntityId =  A.AlarmLevel_EntityId LEFT JOIN AlarmRule as D on D.EntityId = A.AlarmRuleId ;";
//
//    //查询规则信息的sql --> 也是一下把所有的站都查出来, --> 在代码里面去做站的区分
//    String ruleSql = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=35;";
//
//    //装填告警信息
//    public void setAlarmListInfo(){
//        jdbcHikariTemplate.queryForList(alarmSql,);
//    }
//
//    //装填规则信息
//    public void setRuleListInfo(){
//        jdbcHikariTemplate.queryForList();
//    }
//
//
//
//
//
//}
