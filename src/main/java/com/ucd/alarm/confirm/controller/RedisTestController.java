package com.ucd.alarm.confirm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucd.alarm.confirm.domain.UserTestDO;
import com.ucd.alarm.confirm.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisCluster;

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
public class RedisTestController {

    /** 测试 redis中存储的过期时间60s */
    private static int ExpireTime = 60;

    //@Autowired
   // JedisCluster jedisCluster;

    @Resource
    private RedisUtil redisUtil;

    @GetMapping("set")
    public boolean redisset(String key, String value){
        UserTestDO userTestDO = new UserTestDO();
        userTestDO.setId(Long.valueOf(1));
        userTestDO.setGuid(String.valueOf(1));
        userTestDO.setName("zhangsan");
        userTestDO.setAge(20);
        userTestDO.setCreateTime(new Date());


        return redisUtil.set(key,value);
    }

    @GetMapping("get")
    public Object redisget(String key, String item) throws JsonProcessingException {
        Collection<Object> strings1 = new ArrayList<>();
        List<Map<String,Object>> list = new ArrayList<>();
        strings1.add(key);
        strings1.add(item);
        List<Object> values = redisUtil.hMultiGet("db0",strings1);
        int i = 0;
        for (Object k: values) {
            System.out.println(k.toString());
        }

        /** 单个hashkey管道查询方案 */
        List<String> keys = new ArrayList<>();
        List<String> hashKeys = new ArrayList<>();
        List<Map<String, String>> hashList = new ArrayList<Map<String, String>>();

        keys.add("db0");
        hashKeys.add(key);
        hashKeys.add(item);
        List<Object> list2 = redisUtil.pipelinedOne(keys,hashKeys);
            for (Object k: list2) {
            // k is the key
            // and here is the value corresponding to the key k
            System.out.println("单个hashhey查询方案"+k.toString());

        }
        return null;
    }


    @RequestMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }

    /**
     * @author Crayon
     * @Description 获取批量keys对应的列表中，指定的hash键值对列表       
     * @date 2020/6/7 1:02 下午 
     * @params []
     * @exception  
     * @return java.util.List<java.util.Map<java.lang.String,java.lang.String>>  
     */
    @PostMapping("getSel")
    public List<Map<String, String>> getSelectiveHashsList(String key1,String key2){
        List<String> keys = new ArrayList<>();
        List<String> hashKeys = new ArrayList<>();
        List<Map<String, String>> hashList = new ArrayList<Map<String, String>>();
        keys.add("db0");
        hashKeys.add(key1);
        hashKeys.add(key2);
        List<Object> list1 = Collections.singletonList(redisUtil.pipelinedList(keys, hashKeys));
        for (Object k: list1) {
            // k is the key
            // and here is the value corresponding to the key k
            System.out.println("批量hashhey查询方案"+k.toString());
        }
        return null;
    }

}
