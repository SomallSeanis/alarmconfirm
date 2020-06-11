package com.ucd.alarm.confirm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;
import com.ucd.alarm.confirm.enums.PointValueEnum;
import com.ucd.alarm.confirm.service.AlarmRuleService;
import com.ucd.alarm.confirm.service.AlarmService;
import com.ucd.alarm.confirm.utils.MemoryCacheUtils;
import com.ucd.alarm.confirm.utils.StringRedisTemplateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @ClassName: AlarmRedisServiceImpl
 * @Description: 从Alarm Redis中数据逻辑处理接口实现类
 * @Author: Crayon
 * @CreateDate: 2020/6/10 5:54 下午
 * @Version 1.0
 * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Repository
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlarmServiceImpl implements AlarmService {
    /**
     * 点值类型
     */
    public static final String POINT_TYPE = "t";

    @Resource
    private StringRedisTemplateUtil stringRedisTemplateUtil;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    AlarmRuleService alarmRuleService;

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @throws
     * @author Crayon
     * @Description 查询并格式化告警信息数据
     * @date 2020/6/10 6:22 下午
     * @params [redisKeys, hashKeys]
     */
    @Override
    public Map<String, Map<String, String>> hashMapListStream(List<String> redisKeys, List<String> hashKeys){
        List<Map<String, String>> hashMapListStream = new ArrayList<Map<String, String>>();
        // 查询告警缓存数据
        List<Object> alarmRedisList = stringRedisTemplateUtil.pipelinedList(redisKeys, hashKeys);
        // todo 测试过后再启用此方法
        // Optional.ofNullable(alarmRedisList.);
        Map<String, Map<String, String>> hashMapMapStream = new LinkedHashMap<>(16);
        // List<Object> = List<List<String>>
        if (!ObjectUtils.isEmpty(alarmRedisList)) {
            alarmRedisList.forEach(obj -> {
                List<String> hashValueListParallelStream = new ArrayList<>();
                if (obj instanceof List<?>) {
                    // obj = List<String>  redis中的Value值 JSON对象
                    ((List<String>) obj).stream().forEachOrdered(o -> {
                        JSONObject jsonObject = JSONObject.parseObject(o);
                        String type = jsonObject.getString(POINT_TYPE);
                        String fieldName = this.getPointField(type, jsonObject);
                        hashValueListParallelStream.add(fieldName);
                    });
                    Map<String, String> resultMap = new LinkedHashMap<String, String>(16);
                    if (hashKeys.size() != hashValueListParallelStream.size()) {
                        log.error("数据异常，缺数据");
                    }
                    for (int i = 0; i < hashValueListParallelStream.size(); i++) {
                        resultMap.put(hashKeys.get(i), hashValueListParallelStream.get(i));
                    }
                    hashMapListStream.add(resultMap);
                }
            });
            for (int i = 0; i < redisKeys.size(); i++) {
                hashMapMapStream.put(redisKeys.get(i), hashMapListStream.get(i));
            }
        }
        return hashMapMapStream;
    }

    /**
     * @return java.lang.Boolean
     * @throws
     * @author Crayon
     * @Description 修改各个车站最终设备告警等级结果
     * @date 2020/6/11 10:31 下午
     * @params []
     */
    @Override
    public Boolean updataAlarmLevelResult(Integer stationId) throws Exception {
        // 获取告警表信息
        Map<String, List<AlarmRealTimeInfos>> mapByStationId = MemoryCacheUtils.getMapByStationId(stationId);

        // 获取规则表Map  key为stationId_pointId，
        Map<String, List<AlarmRule>> ruleMapByStationId = MemoryCacheUtils.getRuleMapByStationId(stationId);

        // 获取告警信息表中的所有stationId_pointId
        List<String> listStationIdPointId = mapByStationId.keySet().stream().collect(Collectors.toList());

        // 查询redis中所有点值信息
        Map<String, Map<String, String>> stringMapMap = this.hashMapListStream(this.getRedisKeyList(), listStationIdPointId);

        if(ObjectUtils.isEmpty(stringMapMap)){
            log.error("【{}】:此车站redis信息异常", stationId);
            return false;
        }
        final Map<String, String> mapValue = stringMapMap.get("db0");
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

            alarmRealTimeInfos = alarmRealTimeInfos.parallelStream()
                    .collect(Collectors.collectingAndThen(Collectors.toCollection(()
                            -> new TreeSet<>(Comparator.comparing(AlarmRealTimeInfos::getAlarmType))), ArrayList::new));
            // 如果为size 1 所有值相同
            if (alarmRealTimeInfos.size() != 1) {
                // 报异常 日志
                log.error("【{}】:告警类型不唯一,跳过本次操作", stationIdPointId);
                return;
            }
            // 获取告警类型
            Integer alarmType = alarmRealTimeInfos.get(0).getAlarmType();
            // 获取规则数据
            List<AlarmRule> alarmRulesList = ruleMapByStationId.get(stationIdPointId);
            // 根据当前告警类型获取告警规则数据
            alarmRulesList = alarmRulesList.stream().filter(type -> type.getAlarmType() == alarmType).collect(Collectors.toList());
            // 调取 判断告警等级接口
            alarmRuleService.doRuleCheck(stationId, pointValue, alarmOrderMax, alarmType, maxTime, alarmRulesList);
        });

        return true;
    }

    /**
     * @return java.lang.String
     * @throws
     * @author Crayon
     * @Description 根据类型判断 所要获取的字段的key值
     * @date 2020/6/11 11:59 上午
     * @params [type]
     */
    private String getPointField(String type, JSONObject jsonObject) {
        String fieldName = null;
        // 判断类型
        if (PointValueEnum.INTEGER_I.getType().equals(type) || PointValueEnum.BOOLEAN_I.getType().equals(type)) {
            fieldName = jsonObject.getString(PointValueEnum.INTEGER_I.getFieldName());
        }

        if (PointValueEnum.STRING_S.getType().equals(type) || PointValueEnum.ARRAY_S.getType().equals(type)) {
            fieldName = jsonObject.getString(PointValueEnum.STRING_S.getFieldName());
        }


        if (PointValueEnum.LONG_L.getType().equals(type)) {
            fieldName = jsonObject.getString(PointValueEnum.LONG_L.getFieldName());
        }


        if (PointValueEnum.DOUBLE_D.getType().equals(type)) {
            fieldName = jsonObject.getString(PointValueEnum.DOUBLE_D.getFieldName());
        }


        if (PointValueEnum.DWORD.getType().equals(type)) {
            fieldName = jsonObject.getString(PointValueEnum.DWORD.getFieldName());
        }
        return fieldName;
    }

    /**
     * @return java.util.List<java.lang.String>
     * @throws
     * @author Crayon
     * @Description 所有有redis key
     * @date 2020/6/11 10:37 下午
     * @params []
     */
    public List<String> getRedisKeyList() {
        List<String> list = new ArrayList<>();
        list.add(BusinessConstants.REDIS_KEY);
        return list;
    }


}
