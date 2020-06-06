package com.ucd.alarm.confirm.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucd.alarm.confirm.domain.UserTestDO;
import com.ucd.alarm.confirm.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    JedisCluster jedisCluster;

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
//        Collection<Object> strings = new ArrayList<>();
//        strings.add("2_6579200");
//        strings.add("2_4983");
        Collection<Object> strings1 = new ArrayList<>();
        List<Map<String,Object>> list = new ArrayList<>();
        Map<String,Object> map =new HashMap<>();
        Map<String,Object> map1 =new HashMap<>();
        map.put("2_6579200","2_6579200");
        map1.put("2_4983","2_6579200");
        list.add(map);
        list.add(map1);
        strings1.add(list);
        List<Object> values = redisUtil.hMultiGet(key,strings1);
        int i = 0;
        for (Object k: values) {
            // k is the key
            // and here is the value corresponding to the key k
            System.out.println(k.toString());

        }

        jedisCluster.hmget("db0","2_6579200,2_4983");
        return null;

    }

    @RequestMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }
}
