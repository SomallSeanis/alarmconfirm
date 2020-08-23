package com.ucd.alarm.confirm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * @ClassName: AlarmRule
 * @Description: TODO
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

//告警规则表对应的实体类
public class AlarmRule implements Serializable {
    /**
     * 告警规则Id
     */
    private String id;
    /**
     * 车站ID
     */
    private int stationId;
    /**
     * 点值ID
     */
    private int pointId;
    /**
     * 告警类型
     */
    private int alarmType;
    /**
     * 告警等级
     */
    private int alarmOrder;
    /**
     * 告警高限
     */
    private float highLimit;
    /**
     * 告警低限
     */
    private float lowLimit;
    /**
     * 告警数据源
     */
    private String alarmSource;

}