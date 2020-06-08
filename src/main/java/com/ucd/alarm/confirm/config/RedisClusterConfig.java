//package com.ucd.alarm.confirm.config;
//
//import com.fasterxml.jackson.annotation.JsonAutoDetect;
//import com.fasterxml.jackson.annotation.PropertyAccessor;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisClusterConfiguration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisNode;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.*;
//import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPoolConfig;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
///**
// * @ClassName: RedisClusterConfig
// * @Description: redis 集群配置类
// * @Author: Crayon
// * @CreateDate: 2020/6/5 7:43 下午
// * @Version 1.0
// * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
// **/
//@Configuration
//@ConditionalOnClass({JedisCluster.class})
//public class RedisClusterConfig {
//    @Value("${spring.redis.cluster.nodes}")
//    private String clusterNodes;
//    @Value("${spring.redis.timeout}")
//    private int timeout;
//    @Value("${spring.redis.lettuce.pool.max-idle}")
//    private int maxIdle;
//    @Value("${spring.redis.lettuce.pool.max-wait}")
//    private long maxWaitMillis;
//
//
//    @Bean
//    public JedisCluster getJedisCluster() {
//        String[] cNodes = clusterNodes.split(",");
//        Set<HostAndPort> nodes = new HashSet<>();
//        //分割出集群节点
//        for (String node : cNodes) {
//            String[] hp = node.split(":");
//            nodes.add(new HostAndPort(hp[0], Integer.parseInt(hp[1])));
//        }
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        //创建集群对象
//        return new JedisCluster(nodes, timeout, jedisPoolConfig);
//    }
//
//    @Bean(name = "jedisClusterConnectionFactory")
//    @Qualifier("jedisClusterConnectionFactory")
//    public JedisConnectionFactory jedisClusterConnectionFactory() {
//        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
//        RedisClusterConfiguration configuration = new RedisClusterConfiguration();
//        List<RedisNode> nodes = new ArrayList<>();
//        String[] cNodes = clusterNodes.split(",");
//        for (String port : cNodes) {
//            String[] hp = port.split(":");
//            nodes.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
//        }
//
//        configuration.setClusterNodes(nodes);
//        jedisPoolConfig.setMaxIdle(maxIdle);
//        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
//        JedisConnectionFactory factory = new JedisConnectionFactory(configuration, jedisPoolConfig);
//        return factory;
//    }
//
//    /**
//     * retemplate相关配置
//     *
//     * @param factory
//     * @return
//     */
//    @Bean(name = "redisClusterTemplate")
//    @Qualifier("redisClusterTemplate")
//    public RedisTemplate<String, Object> redisClusterTemplate(@Qualifier("jedisClusterConnectionFactory") RedisConnectionFactory factory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        // 配置连接工厂
//        template.setConnectionFactory(factory);
//
//        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
//        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
//
//        ObjectMapper om = new ObjectMapper();
//        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
//        // 注释这行配置, 解决 `Unexpected token (VALUE_STRING), expected START_ARRAY: need JSON Array to contain As.WRAPPER_ARRAY type information for class java.util.Date`
////        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        jacksonSeial.setObjectMapper(om);
//
//        // 值采用json序列化
//        template.setValueSerializer(jacksonSeial);
//        //使用StringRedisSerializer来序列化和反序列化redis的key值
//        template.setKeySerializer(new StringRedisSerializer());
//
//        // 设置hash key 和value序列化模式
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(jacksonSeial);
//        template.afterPropertiesSet();
//
//        return template;
//
//    }
//
//    /**
//     * 对hash类型的数据操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public HashOperations<String, String, Object> hashClusterOperations(@Qualifier("redisClusterTemplate") RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForHash();
//    }
//
//    /**
//     * 对redis字符串类型数据操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public ValueOperations<String, Object> valueClusterOperations(@Qualifier("redisClusterTemplate") RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForValue();
//    }
//
//    /**
//     * 对链表类型的数据操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public ListOperations<String, Object> listClusterOperations(@Qualifier("redisClusterTemplate") RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForList();
//    }
//
//    /**
//     * 对无序集合类型的数据操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public SetOperations<String, Object> setClusterOperations(@Qualifier("redisClusterTemplate") RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForSet();
//    }
//
//    /**
//     * 对有序集合类型的数据操作
//     *
//     * @param redisTemplate
//     * @return
//     */
//    @Bean
//    public ZSetOperations<String, Object> zSetClusterOperations(@Qualifier("redisClusterTemplate") RedisTemplate<String, Object> redisTemplate) {
//        return redisTemplate.opsForZSet();
//    }
//
//}
