package com.ucd.alarm.confirm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucd.alarm.confirm.enums.PointValueEnum;
import com.ucd.alarm.confirm.service.AlarmService;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @throws
     * @author Crayon
     * @Description 查询并格式化告警信息数据
     * @date 2020/6/10 6:22 下午
     * @params [redisKeys, hashKeys]
     */
    @Override
    public Map<String, Map<String, String>> hashMapListStream(List<String> redisKeys, List<String> hashKeys) throws Exception {
        List<Map<String, String>> hashMapListStream = new ArrayList<Map<String, String>>();
        // 查询告警缓存数据
        List<Object> alarmRedisList = stringRedisTemplateUtil.pipelinedList(redisKeys, hashKeys);
        // todo 测试过后再启用此方法
        //Optional.ofNullable(alarmRedisList);
        Map<String, Map<String, String>> hashMapMapStream = new LinkedHashMap<>(16);
        // List<Object> = List<List<String>>
        if (!ObjectUtils.isEmpty(alarmRedisList)) {
            alarmRedisList.forEach(obj -> {
                List<String> hashValueListParallelStream = new ArrayList<>();
                if (obj instanceof List<?>) {
                    // obj = List<String>  redis中的Value值 JSON对象
                    ((List<String>) obj).parallelStream().forEachOrdered(o -> {
                        JSONObject jsonObject = JSONObject.parseObject(o);
                        String type = jsonObject.getString(POINT_TYPE);
                        this.getPointField(type, jsonObject);
                        hashValueListParallelStream.add(type);
                    });
                    Map<String, String> resultMap = new LinkedHashMap<String, String>();
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
}
