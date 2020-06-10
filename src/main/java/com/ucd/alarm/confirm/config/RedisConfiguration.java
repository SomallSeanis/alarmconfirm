package com.ucd.alarm.confirm.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.ObjectUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

/**
 * @ClassName: RedisConfiguration
 * @Description: redis 集群/单机版配置  特此说明：此配置是springboot2.x版本整合redis(单机/集群)(使用lettuce)
 * @Author: Crayon
 * @CreateDate: 2020/6/6 2:48 下午
 * @Version 1.0
 * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
 **/
@Configuration
public class RedisConfiguration {
    @Value("${spring.redis.cluster.nodes}")
    private String clusterNodes;
    @Value("${spring.redis.timeout}")
    private int timeout;
    @Value("${spring.redis.lettuce.pool.max-idle}")
    private Integer maxIdle;
    @Value("${spring.redis.lettuce.pool.min-idle}")
    private Integer minIdle;
    @Value("${spring.redis.lettuce.pool.max-wait}")
    private Long maxWaitMillis;
    @Value("${spring.redis.local.host}")
    private String hostName;
    @Value("${spring.redis.local.port}")
    private int port;
    @Value("${spring.redis.local.password}")
    private String password;
    @Value("${spring.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Bean
    LettuceConnectionFactory lettuceConnectionFactory() {
        // 连接池配置
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(maxIdle == null ? 8 : maxIdle);
        poolConfig.setMinIdle(minIdle == null ? 1 : minIdle);
        poolConfig.setMaxWaitMillis(maxWaitMillis == null ? 5000L : maxWaitMillis);
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration.builder()
                .poolConfig(poolConfig)
                .build();
        // redis 单机版本  RedisStandaloneConfiguration redisConfig = this.redisStandaloneConfiguration();
        // redis 集群版本配置
        RedisClusterConfiguration redisConfig = this.redisClusterConfiguration();
        // 无密码，暂时不用
        // redisConfig.setPassword(password);
        return new LettuceConnectionFactory(redisConfig, lettucePoolingClientConfiguration);

    }

    /**
     * @return org.springframework.data.redis.core.StringRedisTemplate
     * @throws
     * @author Crayon
     * @Description StringRedisTemplate使用的是StringRedisSerializer
     * @date 2020/6/10 4:59 下午
     * @params [lettuceConnectionFactory]
     */
    @Bean("stringRedisTemplateLettuce")
    @Qualifier("stringRedisTemplateLettuce")
    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
        stringRedisTemplate.setConnectionFactory(lettuceConnectionFactory);
        return stringRedisTemplate;
    }


    /**
     * @return org.springframework.data.redis.core.RedisTemplate<java.lang.String, java.lang.Object>
     * @throws
     * @author Crayon
     * @Description RedisTemplate使用的是JdkSerializationRedisSerializer
     * @date 2020/6/10 4:59 下午
     * @params [lettuceConnectionFactory]
     */
    @Bean("redisTemplateLettuce")
    @Qualifier("redisTemplateLettuce")
    public RedisTemplate<String, Object> redisTemplate(LettuceConnectionFactory lettuceConnectionFactory) {
       /* RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(lettuceConnectionFactory);
        //序列化类
        MyRedisSerializer myRedisSerializer = new MyRedisSerializer();
        //key序列化方式
        template.setKeySerializer(myRedisSerializer);
        //value序列化
        template.setValueSerializer(myRedisSerializer);
        //value hashmap序列化
        template.setHashValueSerializer(myRedisSerializer);
        return template;*/

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // 配置连接工厂
        template.setConnectionFactory(lettuceConnectionFactory);
        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
        Jackson2JsonRedisSerializer jacksonSeial = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        // 注释这行配置, 解决 `Unexpected token (VALUE_STRING), expected START_ARRAY: need JSON Array to contain As.WRAPPER_ARRAY type information for class java.util.Date`
        // om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSeial.setObjectMapper(om);
        // 值采用json序列化
        template.setValueSerializer(jacksonSeial);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        // 设置hash key 和value序列化模式
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(jacksonSeial);
        template.afterPropertiesSet();

        return template;

    }

    /**
     * @return void
     * @throws
     * @author Crayon
     * @Description 配置版本：单机redis
     * @date 2020/6/7 4:20 下午
     * @params []
     */
    private RedisStandaloneConfiguration redisStandaloneConfiguration() {
        RedisStandaloneConfiguration redisConfig = new RedisStandaloneConfiguration();
        // 当IP地址和端口号写在一起时候请用下面方式解析 如：127.0.0.1:9379
        // redisConfig.setHostName(hostName == null || "".equals(hostName) ? "localhost" : hostName.split(":")[0]);
        // redisConfig.setPort(Integer.valueOf(hostName == null || "".equals(hostName) ? "6379" : hostName.split(":")[1]));
        redisConfig.setHostName(hostName);
        redisConfig.setPort(port);
        if (!ObjectUtils.isEmpty(password)) {
            redisConfig.setPassword(password);
        }
        return redisConfig;
    }

    /**
     * @return org.springframework.data.redis.connection.RedisClusterConfiguration
     * @throws
     * @author Crayon
     * @Description redis集群版本 配置类
     * @date 2020/6/7 4:32 下午
     * @params []
     */
    private RedisClusterConfiguration redisClusterConfiguration() {
        RedisClusterConfiguration redisConfig = new RedisClusterConfiguration();
        Set<RedisNode> nodes = new HashSet<>();
        String[] cNodes = clusterNodes.split(",");
        for (String port : cNodes) {
            String[] hp = port.split(":");
            nodes.add(new RedisNode(hp[0], Integer.parseInt(hp[1])));
        }
        redisConfig.setClusterNodes(nodes);
        // 跨集群执行命令时要遵循的最大重定向数量
        redisConfig.setMaxRedirects(3);
        return redisConfig;
    }


    /**
     * @return org.springframework.data.redis.connection.RedisSentinelConfiguration
     * @throws
     * @author Crayon
     * @Description 哨兵 redis
     * @date 2020/6/7 4:33 下午
     * @params []
     */
    private RedisSentinelConfiguration redisSentinelConfiguration() {
        return null;
    }

    /**
     * @author Crayon
     * @Description 静态内部类，根据对应功能需求进行合理序列化方式替换
     * @date 2020/6/7 4:03 下午
     * @params
     * @exception
     * @return
     */
    static class MyRedisSerializer implements RedisSerializer<Object> {
        @Override
        public byte[] serialize(Object o) throws SerializationException {
            return serializeObj(o);
        }

        @Override
        public Object deserialize(byte[] bytes) throws SerializationException {
            return deserializeObj(bytes);
        }

        /**
         * @return byte[]
         * @throws
         * @author Crayon
         * @Description 序列化
         * @params [object]
         */
        private static byte[] serializeObj(Object object) {
            ObjectOutputStream oos = null;
            ByteArrayOutputStream baos = null;
            try {
                baos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(baos);
                oos.writeObject(object);
                byte[] bytes = baos.toByteArray();
                return bytes;
            } catch (Exception e) {
                throw new RuntimeException("序列化失败!", e);
            }
        }

        /**
         * @return java.lang.Object
         * @throws
         * @author Crayon
         * @Description 反序列化
         * @params [bytes]
         */
        private static Object deserializeObj(byte[] bytes) {
            if (bytes == null) {
                return null;
            }
            ByteArrayInputStream bais = null;
            try {
                bais = new ByteArrayInputStream(bytes);
                ObjectInputStream ois = new ObjectInputStream(bais);
                return ois.readObject();
            } catch (Exception e) {
                throw new RuntimeException("反序列化失败!", e);
            }
        }
    }

}


