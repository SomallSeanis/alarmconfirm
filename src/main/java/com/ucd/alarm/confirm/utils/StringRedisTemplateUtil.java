package com.ucd.alarm.confirm.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName: StringRedisTemplateUtil
 * @Description: 基于StringRedisTemplate的工具类。StringRedisConnection连接对应->StringRedisTemplated类
 * @Author: Crayon
 * @CreateDate: 2020/6/9 1:17 下午
 * @Version 1.0
 * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
 **/
@Component
public class StringRedisTemplateUtil {

    private static final Logger log = LoggerFactory.getLogger(StringRedisTemplateUtil.class);

    @Qualifier("stringRedisTemplateLettuce")
    private StringRedisTemplate stringRedisTemplate;

    public StringRedisTemplateUtil(@Qualifier("stringRedisTemplateLettuce") StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
     * @throws
     * @author Crayon
     * @Description 批量 【pipleline管道查询】
     * 使用StringRedisTemplate原因，批量HashKeys查询，使用管道方式速度快，StringRedisTemplate底层参数为String类型。
     * 存取的数据就是字符串类型数据的时候，使用StringRedisTemplate即可。
     * @date 2020/6/7 1:07 下午
     */
    public List<Object> pipelinedList(List<String> redisKey, List<String> hashKeys) {
        List<Object> redisResult = stringRedisTemplate.executePipelined(
                new RedisCallback<List<String>>() {
                    @Override
                    public List<String> doInRedis(RedisConnection connection) throws DataAccessException {
                        // 这种调用方式 需要使用StringRedisTemplate处理缓存模式
                        StringRedisConnection stringRedisConnection = (StringRedisConnection) connection;
                        // 可能有多个redis的Key
                        redisKey.forEach(key -> {
                            stringRedisConnection.hMGet(key, hashKeys.toArray(new String[hashKeys.size()]));
                        });
                        return null;
                    }
                });
        return redisResult;
    }


    /**
     * @return java.lang.String
     * @throws
     * @author Crayon
     * @Description 获取String类型的value
     * @date 2020/6/10 7:11 下午
     * @params [name]
     */
    public String findName(String name) {
        if (name == null) {
            log.error("===============key为null======================================================");
        }
        return stringRedisTemplate.opsForValue().get(name);
    }

    /**
     * @return java.lang.String
     * @throws
     * @author Crayon
     * @Description 添加String类型的key-value
     * @date 2020/6/10 7:11 下午
     * @params [name, value]
     */
    public String setNameValue(String name, String value) {
        log.info("==================添加String类型的key-value========================================");
        stringRedisTemplate.opsForValue().set(name, value);
        return name;
    }

    /**
     * @return java.lang.String
     * @throws
     * @author Crayon
     * @Description 根据key删除redis的数据
     * @date 2020/6/10 7:11 下午
     * @params [name]
     */
    public String delNameValue(String name) {
        stringRedisTemplate.delete(name);
        return name;
    }

    /**
     * @return java.util.List<java.lang.String>
     * @throws
     * @author Crayon
     * @Description 根据key获取list类型的value(范围)
     * @date 2020/6/10 7:11 下午
     * @params [key, start, end]
     */
    public List<String> findList(String key, int start, int end) {
        log.info("=====================按照范围查询redis中List类型=======================================");
        return stringRedisTemplate.opsForList().range(key, start, end);
    }


    /**
     * @return long
     * @throws
     * @author Crayon
     * @Description 插入多条数据
     * @date 2020/6/10 7:12 下午
     * @params [key, value]
     */
    public long setList(String key, List<String> value) {
        log.info("=========================redis List type insert ======================================");
        return stringRedisTemplate.opsForList().rightPushAll(key, value);
    }

    /**
     * @return java.lang.String
     * @throws
     * @author Crayon
     * @Description 获取list最新记录（右侧）
     * @date 2020/6/10 7:12 下午
     * @params [key]
     */
    public String findLatest(String key) {
        log.info("=============================rides List latest rigth==================================");
        return stringRedisTemplate.opsForList().index(key, stringRedisTemplate.opsForList().size(key) - 1);
    }

    /**
     * @return java.util.Map<java.lang.Object, java.lang.Object>
     * @throws
     * @author Crayon
     * @Description 查询hash
     * @date 2020/6/10 7:12 下午
     * @params [key]
     */
    public Map<Object, Object> findHash(String key) {
        log.info("=================================== redis hash =========================================");
        return stringRedisTemplate.opsForHash().entries(key);
    }


    /**
     * @return java.util.Set<java.lang.Object>
     * @throws
     * @author Crayon
     * @Description 查询hash中所有的key
     * @date 2020/6/10 7:12 下午
     * @params [key]
     */
    public Set<Object> findHashKeys(String key) {
        log.info("====================================== All keys of hash ===============================");
        return stringRedisTemplate.opsForHash().keys(key);
    }

    /**
     * @return java.util.List<java.lang.Object>
     * @throws
     * @author Crayon
     * @Description 查询hash中所有的value
     * @date 2020/6/10 7:12 下午
     * @params [key]
     */
    public List<Object> findHashValues(String key) {
        log.info("===================================== All values of hash ==============================");
        return stringRedisTemplate.opsForHash().values(key);
    }

    /**
     * @return long
     * @throws
     * @author Crayon
     * @Description 插入hash数据
     * @date 2020/6/10 7:10 下午
     * @params [key, map]
     */
    public long insertHash(String key, Map<String, Object> map) {
        log.info("====================================== insert hashes into redis ========================");
        stringRedisTemplate.opsForHash().putAll(key, map);
        return map.size();
    }
}
