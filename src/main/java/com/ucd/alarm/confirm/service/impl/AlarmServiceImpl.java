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
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
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

    private final AlarmRuleService alarmRuleService;

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @throws
     * @author Crayon
     * @Description 查询并格式化告警信息数据
     * @date 2020/6/10 6:22 下午
     * @params [redisKeys, hashKeys]
     */
    @Override
    public Map<String, Map<String, String>> hashMapListStream(List<String> redisKeys, List<String> hashKeys) {
        List<Map<String, String>> hashMapListStream = new ArrayList<Map<String, String>>();
        // 查询告警缓存数据
        //这个就是去查Redis了
        List<Object> alarmRedisList = stringRedisTemplateUtil.pipelinedList(redisKeys, hashKeys);
        Map<String, Map<String, String>> hashMapMapStream = new LinkedHashMap<>(16);
        // todo 测试过后再启用此方法
        // Optional.ofNullable(alarmRedisList.);
        /** List<Object> = List<List<String>>*/
        //查Redis有值,并且不为空
        if (!ObjectUtils.isEmpty(alarmRedisList) && alarmRedisList.size() > 0) {
            alarmRedisList.forEach(obj -> {
                List<String> hashValueListParallelStream = new ArrayList<>();
                if (obj instanceof List<?>) {
                    List<String> linkedHashMaps = (List<String>) obj;
                    List<Integer> errorPointIndex = new ArrayList<>();
                    List<String> errorPoint = new ArrayList<>();
                    for (int k = 0; k < linkedHashMaps.size(); k++) {
                        if (ObjectUtils.isEmpty(linkedHashMaps.get(k)) || StringUtils.isEmpty(linkedHashMaps.get(k))) {
                            errorPoint.add(hashKeys.get(k));
                            errorPointIndex.add(k);
                        }
                    }
                    //涉及到数组的移动 ---> 需要改
                    log.info("不可用的点 hashKeys 信息为【{}】", errorPoint.toString());
                    log.info("不可用的点 hashKeys 的位置信息为【{}】", errorPointIndex.toString());

                    final int[] tmp = {0};
                    //把从Redis查出的错误的点删除掉
                    errorPointIndex.stream().forEachOrdered(i -> { //
                        linkedHashMaps.remove(i - tmp[0]); //把错误的点删除掉
                        hashKeys.remove(i - tmp[0]);  //把
                        tmp[0]++;
                    });
                    log.info("================linkedHashMaps：过滤后redis缓存种查找的真实有效的size【{}】", linkedHashMaps.size());
                    log.info("================hashKeys:将不可用的点过滤后size大小为【{}】", hashKeys.size());
                    /** linkedHashMaps = List<String>  redis中的Value值 JSON对象 */
                    linkedHashMaps.parallelStream().forEachOrdered(o -> {
                        JSONObject jsonObject = JSONObject.parseObject(o);
                        String type = jsonObject.getString(POINT_TYPE);
                        String fieldName = this.getPointField(type, jsonObject);
                        hashValueListParallelStream.add(fieldName);
                    });
                    Map<String, String> resultMap = new LinkedHashMap<String, String>(16);

                    // todo 异常情况处理 暂时缺少
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
        log.info(stationId+"进入updata方法");

        // 获取车站ID为stationID的告警表信息
        Map<String, List<AlarmRealTimeInfos>> mapByStationId = MemoryCacheUtils.getMapByStationId(stationId);
        if (ObjectUtils.isEmpty(mapByStationId) || mapByStationId.size() == 0) {
            return false;
        }
        // 获取车站ID为stationID的规则表Map  key为stationId_pointId，
        Map<String, List<AlarmRule>> ruleMapByStationId = MemoryCacheUtils.getRuleMapByStationId(stationId);
        if (ObjectUtils.isEmpty(ruleMapByStationId) || ruleMapByStationId.size() == 0) {
            return false;
        }
        ThreadLocal<Map<String, List<AlarmRule>>> threadRuleLocal = new ThreadLocal<>();
        threadRuleLocal.set(ruleMapByStationId);

        ThreadLocal<Map<String, List<AlarmRealTimeInfos>>> threadAlarmLocal = new ThreadLocal<>();
        threadAlarmLocal.set(mapByStationId);
        // 在threadlocal中操作数据筛选
        for (Map.Entry<String, List<AlarmRealTimeInfos>> entrySet : threadAlarmLocal.get().entrySet()) {
            List<AlarmRealTimeInfos> alarmInfos = entrySet.getValue();
            List<AlarmRealTimeInfos> alarmRealTimeInfosList = new ArrayList<>();
            for (AlarmRealTimeInfos alarmRealTimeInfos : alarmInfos) {
                if (ObjectUtils.isEmpty(alarmRealTimeInfos.getAlarmType())) {
                    continue;
                }
                alarmRealTimeInfosList.add(alarmRealTimeInfos);
            }
            threadAlarmLocal.get().put(entrySet.getKey(), alarmRealTimeInfosList);
        }
        // 获取告警信息表中的所有stationId_pointId
        List<String> listStationIdPointId = threadAlarmLocal.get().keySet().stream().collect(Collectors.toList());
        log.info(stationId+"站点值listStationIdPointId为{}",listStationIdPointId.size());
        // 查询redis中所有点值信息
        Map<String, Map<String, String>> stringMapMap = this.hashMapListStream(this.getRedisKeyList(), listStationIdPointId);
        log.info(stationId+"站点值stationmapsize为{}",stringMapMap.size());
        if (ObjectUtils.isEmpty(stringMapMap)) {
            log.error("【{}】:此车站redis信息异常", stationId);
            return false;
        }
        final Map<String, String> mapValue = stringMapMap.get("db0");
        mapValue.forEach((stationIdPointId, pointValue) -> {
            // 获取 对应告警规则数据
            List<AlarmRealTimeInfos> alarmRealTimeInfos = threadAlarmLocal.get().get(stationIdPointId);
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
            // 获取告警类型(从规则表中筛选出alarmtype)
            String spId = alarmRealTimeInfos.get(0).getSpId();
            String alarmRuleId = alarmRealTimeInfos.get(0).getAlarmRuleId();
            Integer alarmType =0;
            Map<String, List<AlarmRule>> ruleMapByStationIdTmp = MemoryCacheUtils.getRuleMapByStationId(stationId);
            if (ruleMapByStationIdTmp.size() != 0) {
                List<AlarmRule> alarmRules = ruleMapByStationIdTmp.get(spId);
                if (alarmRules != null) {
                    for (AlarmRule alarmRule : alarmRules) {
                        //根据alarm_Rule_id去查找alarmType
                        if (alarmRule.getId().equals(alarmRuleId)) {
                            //这就是找到了,取alarmType
                            alarmType = alarmRule.getAlarmType();
                            break;
                        }
                    }
                }
            }
            // 获取规则数据
            List<AlarmRule> alarmRulesList = threadRuleLocal.get().get(stationIdPointId);
            if (ObjectUtils.isEmpty(alarmRulesList) || alarmRulesList.size() == 0) {
                return;
            }
            // 根据当前告警类型获取告警规则数据
            Integer finalAlarmType = alarmType;
            alarmRulesList = alarmRulesList.stream().filter(type -> finalAlarmType.equals(type.getAlarmType())).collect(Collectors.toList());
            // 调取 判断告警等级接口
            boolean isUpdata = alarmRuleService.doRuleCheck(stationId, pointValue, alarmOrderMax, alarmType, maxTime, alarmRulesList);
        });
        threadAlarmLocal.remove();
        threadRuleLocal.remove();
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
