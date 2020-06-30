package com.ucd.alarm.confirm.msg;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.threadtask.AlarmTaskService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @ClassName: AlarmMsgConsumer
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2019/11/15 17:18
 * @Version 1.0
 * @Copyright: Copyright2018-2019 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

//告警信息的 kafka 消费者类
public class AlarmMsgConsumer {

    //组内竞争,组间共享 -->  不同的组通过同一个 topic 拿到的数据是一样的.
    //优先使用类文件中的groupid, 如果类文件中未定义groupid, 则才会使用配置文件中的groupid, 两者并不会产生冲突.
    private final static String groupid = "alarmconfirm";

    /***
     * @author liuxin
     * @Description 监听topic, 单条消费发送websocket，多节点不同消费者组
     * @date 2019/11/6 10:40
     * @params [record]
     * @exception
     * @return void
     */
    @KafkaListener(topics = "alarm", groupId = groupid, id = "alarm-confirm")
    public void listenAlarmInfo(ConsumerRecord<String, String> record) {
        consumerAlarmInfo(record);
    }

    /***
     * @author liuxin
     * @Description 消费列车相关消息
     * @date 2019/11/6 10:46
     * @params [record], [status:0.websocket,1:db]
     * @exception
     * @return void
     */
    public void consumerAlarmInfo(ConsumerRecord<String, String> record) {
        //java8加入的新类，可以有效防止空指针异常
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            this.dealAlarmInfo(message);
        }
    }

    /***
     * @author liuxin
     * @Description 列车实时数据处理
     * @date 2019/11/6 10:50
     * @params [message], [status:0.websocket,1:db]
     * @exception
     * @return void
     */
    //在这个方法里面,我们需要将信息接入到ConcurrentHashMap中.
    public void dealAlarmInfo(Object message) {
        String alarmInfo = String.valueOf(message);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Map everyAlarmInfo = objectMapper.readValue(alarmInfo, Map.class);
            if (everyAlarmInfo != null && everyAlarmInfo.size() != 0) {
                if (everyAlarmInfo.get("AlarmStatus").equals("Unprocessed")) {
                    //车站id
                    Integer stationId = (Integer) everyAlarmInfo.get("StationId");
                    //点的id
                    Integer pointId = (Integer) everyAlarmInfo.get("PointId");
                    //车站id_点的id
                    String spId = stationId + "_" + pointId;
                    AlarmRealTimeInfos alarmRealTimeInfos = new AlarmRealTimeInfos();

                    //alarm_Rule_id
                    String alarmRuleId = (String) everyAlarmInfo.get("AlarmRuleId");
                    //alarm_order
                   /* Map<String, Object> alarmLevel = (Map<String, Object>) everyAlarmInfo.get("AlarmLevel");
                    Integer alarmOrder = (Integer) alarmLevel.get("Order");*/
                    //alarm_source
                    //kafka
                    //max_time
                    String alarmDateTime = (String) everyAlarmInfo.get("AlarmDateTime");
                    //alarm_type
                    String alarmType = (String) everyAlarmInfo.get("AlarmType");
                    //组装
                    alarmRealTimeInfos.setStationId(stationId);
                    alarmRealTimeInfos.setPointId(pointId);
                    alarmRealTimeInfos.setSpId(spId);
                    alarmRealTimeInfos.setAlarmRuleId(alarmRuleId);
                    //alarmRealTimeInfos.setAlarmOrder(alarmOrder);
                    alarmRealTimeInfos.setAlarmSource("KafKa");
                    alarmRealTimeInfos.setMaxTime(alarmDateTime);
                    //我们这从 ConcurrentHashMap 集合中去拿那个alarmType状态值

                    //我们先循环去判断 --> 看这两个map是否为null, 因为kafka这里执行的比较早,可能 这边已经执行了,但是那边的这两个Map还没有初始化. --> 就会导致空指针异常!
                    while (true) {
                        //false && false //true && true
                        if (!ObjectUtils.isEmpty(AlarmTaskService.excAlarmResultHashMap) && !ObjectUtils.isEmpty(AlarmTaskService.excRuleResultHashMap)) {
                            if (AlarmTaskService.excAlarmResultHashMap.size() == BusinessConstants.STATION_COUNT && AlarmTaskService.excRuleResultHashMap.size() == BusinessConstants.STATION_COUNT) {
                                break;
                            }
                        }
                    }

                    if (!AlarmTaskService.excAlarmResultHashMap.get(stationId) || !AlarmTaskService.excRuleResultHashMap.get(stationId)) {
                        //不符合条件 --> kafka 就先暂停发送数据
//                                kafkaManageService.stop();
                        //一直循环判断是否具备从ConcurrentHashMap取值的条件.
                        //true&& true =true
                        while (true) {
                            if (AlarmTaskService.excAlarmResultHashMap.get(stationId) && AlarmTaskService.excRuleResultHashMap.get(stationId)) {
                                //满足
//                                        kafkaManageService.start();
                                break;
                            }
                        }
                    }

                    // 满足条件之后,我们 我们先从concurrentHashMap里面查 --> 取数据(并且本次数据也不会丢失) --> 因为线程一直在做 while 循环判断
                    Map<String, List<AlarmRule>> ruleMapByStationId = MemoryCacheUtils.getRuleMapByStationId(stationId);
                    if (ruleMapByStationId != null || ruleMapByStationId.size() != 0) {
                        List<AlarmRule> alarmRules = ruleMapByStationId.get(spId);
                        if (alarmRules != null) {
                            for (AlarmRule alarmRule : alarmRules) {
                                //根据alarm_Rule_id去查找alarmType
                                if (alarmRule.getId().equals(alarmRuleId)) {
                                    //这就是找到了,取alarmType
                                    int alarmType1 = alarmRule.getAlarmType();
                                    int alarmOrder = alarmRule.getAlarmOrder();
                                    alarmRealTimeInfos.setAlarmType(alarmType1);
                                    alarmRealTimeInfos.setAlarmOrder(alarmOrder);
                                    break;
                                }
                            }
                        }
                        //组装完毕 --> 将对象添加到告警对应的ConcurrentHashMap中
                        Map<String, List<AlarmRealTimeInfos>> realInfoMapByStationId = MemoryCacheUtils.getMapByStationId(stationId);
                        //不管告警是升高了还是降低了 --> 都覆盖.
                        log.info("stationId" + stationId + "的kafka执行完方法,权限告警表ConcurrentHashMap的大小是" + realInfoMapByStationId.size());
                        ArrayList<AlarmRealTimeInfos> ruleTimeInfoList = new ArrayList<>();
                        ruleTimeInfoList.add(alarmRealTimeInfos);
                        realInfoMapByStationId.put(spId, ruleTimeInfoList);
                    }
                }
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}