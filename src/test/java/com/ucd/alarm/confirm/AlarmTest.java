package com.ucd.alarm.confirm;

import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.service.AlarmService;
import com.ucd.alarm.confirm.threadtask.AlarmTaskService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @ClassName: AlarmTest
 * @Description: 测试类
 * @Author: Crayon
 * @CreateDate: 2020/6/11 2:56 下午
 * @Version 1.0
 * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlarmTest {

    @Autowired
    AlarmTaskService alarmTaskService;
    @Autowired
    AlarmService alarmService;

    /**
     * @return void
     * @throws
     * @author Crayon
     * @Description 获取Redis中数据 测试方法
     * @date 2020/6/12 1:50 上午
     * @params []
     */
    @Test
    public void parallelStreamTest() {
        List<String> hashKeys = new ArrayList<>();
        List<String> keys = new ArrayList<>();
        keys.add("db0");
      //  keys.add("db1");
        hashKeys.add("2_13050000");
        hashKeys.add("2_1727");
        hashKeys.add("2_1887400");
        hashKeys.add("123456");
        hashKeys.add("2_36171");
        hashKeys.add("2_4690800");
        hashKeys.add("2_4983");
        hashKeys.add("2_54016");
        hashKeys.add("2_54017");
        hashKeys.add("2_54018");
        hashKeys.add("2_54019");
        hashKeys.add("2_54020");
        hashKeys.add("2_54021");
        hashKeys.add("2_54022");
        hashKeys.add("2_54023");
        hashKeys.add("2_54024");
        hashKeys.add("2_54025");
        hashKeys.add("2_6579200");
        hashKeys.add("2_6875900");
        hashKeys.add("2_6904900");
        hashKeys.add("2_7510300");

        Map<String, Map<String, String>> mapList = null;
        try {
            mapList = alarmService.hashMapListStream(keys, hashKeys);
            //alarmService.updataAlarmLevelResult(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=================" + mapList.toString());
        System.out.println("=================" + mapList.size());

        // parallelStream数据测试
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list1.add(String.valueOf(i));
        }
        list1.parallelStream().forEachOrdered(p -> {
            list2.add(p);
        });
        System.out.println("大数据量处理parallelStream" + list2.toString());
        System.out.println("大数据量处理parallelStream获取的数据" + list2.size());
    }

    @Test
    public void alarmLevelTest() {
        List<String> keys = new ArrayList<>();
        keys.add("db0");
        keys.add("db1");

        // 1.获取告警表信息
        Map<String, List<AlarmRealTimeInfos>> mapByStationId = MemoryCacheUtils.getMapByStationId(1);
        // 2.获取规则表Map  key为stationId_pointId，
        Map<String, List<AlarmRule>> ruleMapByStationId = MemoryCacheUtils.getRuleMapByStationId(1);
        // 3.获取告警信息表中的所有stationId_pointId
        List<String> listStationIdPointId = mapByStationId.keySet().stream().collect(Collectors.toList());
        // 4.查询redis中所有点值信息
        Map<String, Map<String, String>> stringMapMap = null;
        try {
            stringMapMap = alarmService.hashMapListStream(keys, listStationIdPointId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 5.获取redis key为"db0"中的数据 stationId_pointId 开始-> 5(取key值)->1 根据key值筛选对应的(List<AlarmRealTimeInfos>)->
        // 1）在List<AlarmRealTimeInfos> 中取出最大alarmOrder 值 ， List<AlarmRealTimeInfos> 筛查alarmType 判断是否是一样
        // 如果是一样，取值（alarmType），继续向下执行。
        // ---> Map<String, List<AlarmRule>> 取出List<AlarmRule> --> List<AlarmRule> 筛选出 alarmType = XX 的数据
        // 根据筛选出来的List<AlarmRule>，RedisValue  判断规则
        final Map<String, String> mapValue = stringMapMap.get("db0");
        /** ====================================== 逻辑部分 ==================================================*/
        mapValue.forEach((stationIdPointId, pointValue) -> {
            // 获取 对应告警规则数据
            List<AlarmRealTimeInfos> alarmRealTimeInfos = mapByStationId.get(stationIdPointId);
            // 获取maxTime
            String maxTime = alarmRealTimeInfos.get(0).getMaxTime();
            // 取出数据
            List<Integer> alarmOrderList = alarmRealTimeInfos.parallelStream().map(AlarmRealTimeInfos::getAlarmOrder)
                    .collect(Collectors.toList());
            // 获取alarmOrder最大值
            int alarmOrderMax = Collections.max(alarmOrderList);
            // 去重
            alarmRealTimeInfos = alarmRealTimeInfos.parallelStream()
                    .collect(Collectors.collectingAndThen(Collectors.toCollection(()
                            -> new TreeSet<>(Comparator.comparing(AlarmRealTimeInfos::getAlarmType))), ArrayList::new));
            //如果为1 所有值相同
            if (alarmRealTimeInfos.size() != 1) {
                // 报异常 日志
            }
            // 获取告警类型
            Integer alarmType = alarmRealTimeInfos.get(0).getAlarmType();
            // 获取规则数据
            List<AlarmRule> alarmRulesList = ruleMapByStationId.get(stationIdPointId);
            // 根据当前告警类型获取规则数据
            alarmRulesList = alarmRulesList.stream().filter(type -> type.getAlarmType() == alarmType).collect(Collectors.toList());

            // 获取alarmType 告警类型  筛选告警的值
            long count = alarmOrderList.parallelStream().distinct().count();

        });
    }


}
