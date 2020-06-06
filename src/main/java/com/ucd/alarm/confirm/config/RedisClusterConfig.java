package com.ucd.alarm.confirm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: RedisClusterConfig
 * @Description: redis 集群配置类
 * @Author: Crayon
 * @CreateDate: 2020/6/5 7:43 下午
 * @Version 1.0
 * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
 **/
@Configuration
@ConditionalOnClass({JedisCluster.class})
public class RedisClusterConfig {
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private int maxIdle;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private long maxWaitMillis;


    @Bean
    public JedisCluster getJedisCluster() {
        String[] cNodes = clusterNodes.split(",");
        Set<HostAndPort> nodes =new HashSet<>();
        //分割出集群节点
        for(String node : cNodes) {
            String[] hp = node.split(":");
            nodes.add(new HostAndPort(hp[0],Integer.parseInt(hp[1])));
        }
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        //创建集群对象
        return new JedisCluster(nodes,timeout,jedisPoolConfig);
    }

/*
    @Bean
    JedisConnectionFactory jedisConnectionFactory(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        RedisClusterConfiguration configuration = new RedisClusterConfiguration();
        List<RedisNode> nodes = new ArrayList<>();
        String[] cNodes = clusterNodes.split(",");
        for(String port:cNodes){
            String[] hp = port.split(":");
            nodes.add(new RedisNode(hp[0],Integer.parseInt(hp[1])));
        }

        configuration.setClusterNodes(nodes);
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWaitMillis);
        JedisConnectionFactory factory = new JedisConnectionFactory(configuration,jedisPoolConfig);
        return factory;
    }

    @Bean(name = "redisClusterTemplate")
    @Qualifier("redisClusterTemplate")
    RedisTemplate redisTemplate(){
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        return redisTemplate;
    }*/

   /* *//**
     * @author Crayon
     * @Description  设置数据存入redis 的序列化方式redisTemplate序列化默认使用的jdkSerializeable,
     *               存储二进制字节码,导致key会出现乱码，所以自定义序列化类
     * @date 2020/6/5 7:47 下午
     * @params [redisConnectionFactory]
     * @exception
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.Object,java.lang.Object>
     *//*
    @Bean
    public RedisTemplate<Object,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory)throws UnknownHostException {
        RedisTemplate<Object,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer =new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper objectMapper =new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);

        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

*/

}
