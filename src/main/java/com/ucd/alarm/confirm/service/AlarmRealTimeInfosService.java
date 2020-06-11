package com.ucd.alarm.confirm.service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName: AlarmListService
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/7 11:46
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/

public interface AlarmRealTimeInfosService {

    /***
    * @Description: 获取相关车站的告警信息
    * @param
    * @author  liuxin
    * @return  java.util.List<java.util.Map<java.lang.String,java.lang.Object>>
    * @exception
    * @date   2020/6/7 16:05
    */
    List<Map<String,Object>> getAlarmLists() throws InterruptedException;

}