package com.ucd.alarm.confirm.service;

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

}