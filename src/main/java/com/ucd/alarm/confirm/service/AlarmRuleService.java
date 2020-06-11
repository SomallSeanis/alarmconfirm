package com.ucd.alarm.confirm.service;

import com.ucd.alarm.confirm.entity.AlarmRule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AlarmRuleService
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/7 15:51
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/

public interface AlarmRuleService {
    /***
    * @Description: 获取告警规则并存入缓存Map中
    * @param
    * @author  liuxin
    * @return  java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    * @exception
    * @date   2020/6/11 12:28
    */
    List<Map<String,Object>> getAlarmRuleLists() throws InterruptedException;
    /***
    * @Description: 根据当前点值与告警规则，判断执行告警复归
    * @param stationId
	* @param redisValue
	* @param alarmOrder
	* @param alarmType
	* @param maxTime
	* @param alarmRuleList
    * @author  liuxin
    * @return  void
    * @exception
    * @date   2020/6/12 4:20
    */
    void doRuleCheck(int stationId, String redisValue, int alarmOrder, int alarmType, String maxTime, List<AlarmRule> alarmRuleList);
}