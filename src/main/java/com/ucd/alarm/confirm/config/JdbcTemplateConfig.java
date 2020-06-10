package com.ucd.alarm.confirm.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @ClassName: JdbcTemplateConfig
 * @Description: JdbcTemplate配置类
 * @Author: liuxin
 * @CreateDate: 2020/6/7 14:00
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/

@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hikari")
@Data
public class JdbcTemplateConfig {

    private String jdbcUrl;
    private String driverClassName;
    private String username;
    private String password;
    private String poolName;
    private int minimumIdle;
    private int connectionTimeout;
    private int maximumPoolSize;
    private int idleTimeout;
    private int maxLifetime;
    private boolean autoCommit;


    @Bean(name = "jdbcHikariDataSource")
    @Qualifier("jdbcHikariDataSource")
    public DataSource dataSource() {

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(jdbcUrl);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setPoolName(poolName);
        dataSource.setMinimumIdle(minimumIdle);
        //todo 提示sqlserver不支持getNetWorkTimeOut(),是否删除待定
        dataSource.setConnectionTimeout(connectionTimeout);
        dataSource.setMaximumPoolSize(maximumPoolSize);
        dataSource.setIdleTimeout(idleTimeout);
        dataSource.setMaxLifetime(maxLifetime);
        dataSource.setAutoCommit(autoCommit);

        return dataSource;
    }

    // 此处省略各个属性的get和set方法

    /***
     * @author liuxin
     * @Description 将 JdbcTemplate注入Spring，并指定接口实现类型为jdbcHikariTemplate
     * @date 2020/06/07 14:26
     * @params [dataSource]
     * @exception
     * @return org.springframework.jdbc.core.JdbcTemplate
     */
    @Bean(name = "jdbcHikariTemplate")
    public JdbcTemplate jdbcHikariTemplate(
            @Qualifier("jdbcHikariDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}