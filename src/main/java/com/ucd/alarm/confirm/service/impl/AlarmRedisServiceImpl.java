package com.ucd.alarm.confirm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ucd.alarm.confirm.service.AlarmRedisService;
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
public class AlarmRedisServiceImpl implements AlarmRedisService {

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
    public List<Map<String, String>> hashMapListStream(List<String> redisKeys, List<String> hashKeys) {
        List<Map<String, String>> hashMapListStream = new ArrayList<Map<String, String>>();
        // 查询告警缓存数据
        List<Object> alarmRedisList = stringRedisTemplateUtil.pipelinedList(redisKeys, hashKeys);
        // todo 测试过后再启用此方法
        //Optional.ofNullable(alarmRedisList);
        List<String> hashValueListParallelStream = new ArrayList<>();
        if (!ObjectUtils.isEmpty(alarmRedisList)) {
            alarmRedisList.forEach(obj -> {
                if (obj instanceof List<?>) {
                    ((List<String>) obj).parallelStream().forEachOrdered(o -> {
                        JSONObject jsonObject = JSONObject.parseObject(o);
                        String type = jsonObject.getString("t");
                        // todo 测试 筛选出类型,根据类型获取点值部分还没写，先测试输出关系是否对应
                        hashValueListParallelStream.add(type);
                    });
                }
                Map<String, String> resultMap = new LinkedHashMap<String, String>();
                if (hashKeys.size() == hashValueListParallelStream.size()) {
                    for (int i = 0; i < hashValueListParallelStream.size(); i++) {
                        resultMap.put(hashKeys.get(i), hashValueListParallelStream.get(i));
                    }
                }
                hashMapListStream.add(resultMap);
            });
        }

        return hashMapListStream;
    }
}
