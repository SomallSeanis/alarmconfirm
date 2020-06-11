package com.ucd.alarm.confirm.controller;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.ucd.alarm.confirm.domain.UserTestDO;
import com.ucd.alarm.confirm.utils.RedisTemplateUtil;
import com.ucd.alarm.confirm.utils.StringRedisTemplateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @ClassName: RedisTestController
 * @Description: redis测试类
 * @Author: Crayon
 * @CreateDate: 2020/6/5 4:50 下午
 * @Version 1.0
 * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@RequestMapping("/redis")
@RestController
@Component
public class RedisTestController {

    /**
     * 测试 redis中存储的过期时间60s
     */
    private static int ExpireTime = 60;

    //@Autowired
    // JedisCluster jedisCluster;

    @Resource
    private RedisTemplateUtil redisTemplateUtil;

    @Resource
    private StringRedisTemplateUtil stringRedisTemplateUtil;


    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @GetMapping("set")
    public boolean redisset(String key, String value) {
        UserTestDO userTestDO = new UserTestDO();
        userTestDO.setId(Long.valueOf(1));
        userTestDO.setGuid(String.valueOf(1));
        userTestDO.setName("zhangsan");
        userTestDO.setAge(20);
        userTestDO.setCreateTime(new Date());


        return redisTemplateUtil.set(key, value);
    }

    @GetMapping("get")
    public Object redisget(String key, String item) throws JsonProcessingException {
        Collection<Object> strings1 = new ArrayList<>();
        List<Map<String, Object>> list = new ArrayList<>();
        strings1.add(key);
        strings1.add(item);
        List<Object> values = redisTemplateUtil.hMultiGet("db0", strings1);
        int i = 0;
        for (Object k : values) {
            System.out.println(k.toString());
        }

        /** 单个hashkey管道查询方案 */
        List<String> keys = new ArrayList<>();
        List<String> hashKeys = new ArrayList<>();
        List<Map<String, String>> hashList = new ArrayList<Map<String, String>>();

        keys.add("db0");
        hashKeys.add(key);
        hashKeys.add(item);
        List<Object> list2 = stringRedisTemplateUtil.pipelinedList(keys, hashKeys);
        for (Object k : list2) {
            // k is the key
            // and here is the value corresponding to the key k
            System.out.println("单个hashhey查询方案" + k.toString());

        }
        return null;
    }


    @RequestMapping("expire")
    public boolean expire(String key) {
        return redisTemplateUtil.expire(key, ExpireTime);
    }

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @throws
     * @author Crayon
     * @Description 获取批量keys对应的列表中，指定的hash键值对列表
     * @date 2020/6/7 1:02 下午
     * @params []
     */
    @PostMapping("getSel")
    public List<Map<String, String>> getSelectiveHashsList(String key1, String key2) {
        List<String> keys = new ArrayList<>();
        List<String> hashKeys = new ArrayList<>();
        List<Map<String, String>> hashList = new ArrayList<Map<String, String>>();
        keys.add("db0");
        hashKeys.add(key1);
        hashKeys.add(key2);
        List<Object> list1 = Collections.singletonList(stringRedisTemplateUtil.pipelinedList(keys, hashKeys));
        for (Object k : list1) {
            // k is the key
            // and here is the value corresponding to the key k
            System.out.println("批量hashhey查询方案" + k.toString());
        }
        return null;
    }


    @PostMapping("getStringSel")
    public Map<String, Object> getStringSelectiveHashsList(String key1, String key2, Boolean useParallel) {



        List<String> keys = new ArrayList<>();
        List<String> hashKeys = new ArrayList<>();
        List<Map<String, String>> hashMapListStream = new ArrayList<Map<String, String>>();
        List<Map<String, String>> hashMapList = new ArrayList<Map<String, String>>();

        keys.add("db0");
        hashKeys.add(key1);
        hashKeys.add(key2);
        hashKeys.add("2_36171");
        hashKeys.add("2_4690800");
        hashKeys.add("2_13050000");
        hashKeys.add("2_6904900");
        hashKeys.add("2_7510300");
        hashKeys.add("2_1887400");
        hashKeys.add("2_6875900");
        hashKeys.add("2_54016");

        List<Object> redisResult = stringRedisTemplate.executePipelined(
                new RedisCallback<List<String>>() {
                    @Override
                    public List<String> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                        // 这种调用方式 需要使用StringRedisTemplate处理缓存模式
                        StringRedisConnection stringRedisConnection = (StringRedisConnection) redisConnection;
                        // 测试数据
                        String key = "db0";
                        String hashKey = "2_6579200";
                        stringRedisConnection.hMGet(key, hashKeys.toArray(new String[hashKeys.size()]));
                        return null;
                    } // 自定义序列化
                });


        List<Object> objectList = stringRedisTemplateUtil.pipelinedList(keys, hashKeys);

        if (null == redisResult || redisResult.size() == 0) {
            return null;
        }

        // 将list<Object> 中的Object转成List<String>
        List<String> hashValueListparallelStream = new ArrayList<>();
        List<String> hashValueList = new ArrayList<>();

        // 因为可能有多个db0的库
        redisResult.forEach(obj -> {
            if (obj instanceof List<?>) {
                ((List<String>) obj).parallelStream().forEachOrdered(o -> {
                    JSONObject jsonObject = JSONObject.parseObject(o);
                    String type = jsonObject.getString("t");
                    // String hashValue = String.class.cast(o);
                    // hashValueListparallelStream.add(String.class.cast(o));
                    // 测试 筛选出类型
                    hashValueListparallelStream.add(type);
                });

                // 普通循环模式
                for (Object o : (List<?>) obj) {
                    String hashValue = String.class.cast(o);
                    JSONObject jsonObject = JSONObject.parseObject(hashValue);
                    String type = jsonObject.getString("t");
                    hashValueList.add(type);
                }
            }
            Map<String, String> map = new LinkedHashMap<String, String>();
            for (int i = 0; i < hashValueListparallelStream.size(); i++) {
                map.put(hashKeys.get(i), hashValueListparallelStream.get(i));
            }
            hashMapListStream.add(map);
            System.out.println(" hashMapListStream.add(map);" + hashMapListStream);
            //  转换成功后进行hashKey与value值对应操作
            Map<String, String> map1 = new LinkedHashMap<String, String>();
            for (int i = 0; i < hashValueList.size(); i++) {
                map1.put(hashKeys.get(i), hashValueList.get(i));
            }
            hashMapList.add(map1);

            System.out.println("hashMapList.add(map1);" + hashMapList);
        });

        // 输出：1 ab
        hashMapList.forEach(System.out::println);

        Map<String, Object> resultMap = null;
        if (useParallel) {
            Map<String, Object> resultMapOne = Collections.synchronizedMap(new HashMap<String, Object>());
            keys.parallelStream().forEach(t -> {
                resultMapOne.put(t, redisResult.get(keys.indexOf(t)));
            });
            resultMap = resultMapOne;
        } else {
            Map<String, Object> resultMapTwo = new HashMap<>();
            for (String t : keys) {
                resultMapTwo.put(t, redisResult.get(keys.indexOf(t)));
            }

            resultMap = resultMapTwo;
        }

        System.out.println("===============" + resultMap.toString());
        return resultMap;
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

}
