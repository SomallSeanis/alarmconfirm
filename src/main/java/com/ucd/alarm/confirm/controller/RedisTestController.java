package com.ucd.alarm.confirm.controller;

import com.ucd.alarm.confirm.domain.UserTestDO;
import com.ucd.alarm.confirm.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.Date;

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
    @Autowired
    JedisCluster jedisCluster;
    /** 测试 redis中存储的过期时间60s */
    private static int ExpireTime = 60;

    @Resource
    private RedisUtil redisUtil;

    @RequestMapping("set")
    public boolean redisset(String key, String value){
        UserTestDO userTestDO = new UserTestDO();
        userTestDO.setId(Long.valueOf(1));
        userTestDO.setGuid(String.valueOf(1));
        userTestDO.setName("zhangsan");
        userTestDO.setAge(20);
        userTestDO.setCreateTime(new Date());

        //return redisUtil.set(key,userTestDO,ExpireTime);

        return redisUtil.set(key,value);
    }

    @RequestMapping("get")
    public Object redisget(String key){
        System.out.println("==============="+jedisCluster.get(key));
     //   System.out.println("==============="+redisUtil.get(key));
        return jedisCluster.get(key);

    }

    @RequestMapping("expire")
    public boolean expire(String key){
        return redisUtil.expire(key,ExpireTime);
    }
}
