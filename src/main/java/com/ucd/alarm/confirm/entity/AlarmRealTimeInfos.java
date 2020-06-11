package com.ucd.alarm.confirm.entity;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.RowMapper;

import javax.swing.tree.TreePath;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @ClassName: AlarmRealTimeInfos
 * @Description: 告警信息Entity
 * @Author: liuxin
 * @CreateDate: 2020/6/7 11:28
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmRealTimeInfos implements  Serializable {
    /**
     * Redis 查询的item值  stationId_pointId
     */
    private String spId;
    /**
     * 车站ID
     */
    private int stationId;
    /**
     * 点值ID
     */
    private int pointId;
    /**
     * 告警表中对应的规则ID
     */
    private String alarmRuleId;
    /**
     * 告警等级
     */
    private int alarmOrder;
    /**
     * 告警数据源
     */
    private String alarmSource;
    /**
     * 告警产生最新时间
     */
    private String maxTime;
    /**
     * 告警产生最新时间
     */
    private int alarmType;

}