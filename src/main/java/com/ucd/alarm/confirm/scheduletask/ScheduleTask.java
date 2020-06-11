package com.ucd.alarm.confirm.scheduletask;

import com.ucd.alarm.confirm.config.CommandLineRunnerImpl;
import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.service.AlarmService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @ClassName: ScheduleTask
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/11 17:38
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Component
public class ScheduleTask {

    @Autowired
    AlarmService alarmService;


    @Scheduled(cron = "0/5 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskOne() throws Exception {
        if (CommandLineRunnerImpl.isDown) {
            //获取告警表Map
            Map<String, List<AlarmRealTimeInfos>> mapByStationId = MemoryCacheUtils.getMapByStationId(1);
            //获取规则表Map
            Map<String, List<AlarmRule>> ruleMapByStationId = MemoryCacheUtils.getRuleMapByStationId(1);
            //获取所有点值key
            List<String> list = mapByStationId.keySet().stream().collect(Collectors.toList());
            //查询点值
            Map<String, Map<String, String>> stringMapMap = alarmService.hashMapListStream(this.getRedisKeyList(), list);
            //获取点值集合
            Map<String, String> map = stringMapMap.get("db0");

            for (Map.Entry<String, String> entry : map.entrySet()) {
                float value = 0;
                String stationIdPointId = entry.getKey();
                if (!StringUtils.isEmpty(entry.getValue())) {
                    value = Float.parseFloat(entry.getValue());
                }

                //解析告警表Map，这一步主要判断当前最强的告警是什么等级，以及判断告警类型是什么。
                List<AlarmRealTimeInfos> alarmRealTimeInfosList = mapByStationId.get(stationIdPointId);
                int alarmType = 100;
                int alarmOrder = 100;
                boolean isSameType = true;

                for (AlarmRealTimeInfos alarmRealTimeInfos : alarmRealTimeInfosList) {
                    //循环一个点对应的告警信息List，获取告警等级值最小的，也就是最严重的告警
                    alarmOrder = this.getMinOrder(alarmOrder, alarmRealTimeInfos.getAlarmOrder());
                    //告警类型第一次初始化值为100，实际值只有0,1,2,3
                    if (alarmType == 100) {
                        alarmType = alarmRealTimeInfos.getAlarmType();

                        //判断每条告警是不是一个类型，正常情况下，同一时间，同一个点，只会产生一种类型的告警，
                        //如果不是，则告警异常，需要跳出当前点值的操作循环并报异常。

                    } else if (alarmType != alarmRealTimeInfos.getAlarmType()) {
                        isSameType = false;
                    }
                }
                //判断是同一种告警类型，则继续执行
                if (isSameType) {
                    //解析告警规则表Map，这一步主要根据当前点值与告警规则判断，
                    //在超限情况下，是否告警等级降低，在其他情况下是否告警，从而执行自动确认告警数据库操作。

                    List<AlarmRule> alarmRules = ruleMapByStationId.get(stationIdPointId);
                    //判断告警信息的告警类型为超限告警
                    if (alarmType == 0) {
                        int alarmOrderByRule = 0;
                        //根据当前点值，遍历所有超限规则，判断每一条是否符合，
                        //并在符合的规则中筛选出告警等级最低的一条的告警等级（即告警等级字段数值最大的一条）。
                        for (AlarmRule alarmRule : alarmRules) {
                            if (alarmRule.getAlarmType() == alarmType) {
                                float highLimit = alarmRule.getHighLimit();
                                float lowLimit = alarmRule.getLowLimit();
                                if (value > highLimit && value < lowLimit) {
                                    alarmOrderByRule = this.getBigOrder(alarmOrderByRule, alarmRule.getAlarmOrder());
                                }
                            }

                        }
                        //判断是否告警等级变小
                        if (alarmOrderByRule > alarmOrder) {
                            //执行自动确认sql
                        }
                    }
                    //判断告警信息的告警类型为其他告警
                    if (alarmType != 0) {
                        for (int i = 1; i < alarmRules.size(); i++) {
                            if (i < 2) {
                                float highLimit = alarmRules.get(i).getHighLimit();
                                if (value != highLimit) {
                                    //执行自动确认sql
                                }
                            }

                        }
                    }
                }

//                if (alarmType!=0){
//                    int finalAlarmType = alarmType;
//                    float finalValue = value;
//                    AtomicInteger finalalarmOrderByRule = new AtomicInteger();
//                    alarmRules.stream().filter(alarmRule -> alarmRule.getAlarmType() == finalAlarmType)
//                            .forEach(selectAlarmRule -> {
//                                if(selectAlarmRule.getHighLimit()==finalValue){
//                                    finalalarmOrderByRule.set(this.getBigOrder(finalalarmOrderByRule.get(), selectAlarmRule.getAlarmOrder()));
//                                };
//                            });
//                }

            }


        }

    }

    public List<String> getRedisKeyList() {
        List<String> list = new ArrayList<>();
        list.add("db0");
        return list;
    }

    public int getMinOrder(int tmpOrder, int order) {
        return Math.min(order, tmpOrder);
    }
    public int getBigOrder(int tmpOrder, int order) {
        return Math.max(order, tmpOrder);
    }
}