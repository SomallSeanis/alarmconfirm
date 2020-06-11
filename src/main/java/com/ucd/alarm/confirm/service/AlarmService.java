package com.ucd.alarm.confirm.service;

import java.util.List;
import java.util.Map;

/**
 * @author Crayon
 * @Description 告警 Redis缓存数据获取 接口层
 * @date 2020/6/10 5:55 下午
 * @params
 * @exception
 * @return
 */
public interface AlarmService {

    /**
     * @return java.util.List<java.util.Map < java.lang.String, java.lang.String>>
     * @throws
     * @author Crayon
     * @Description 从redis中查询告警信息，并以key为stationId_pointId,value为点值形式输出
     * @date 2020/6/10 6:21 下午
     * @params [redisKeys, hashKeys]
     */
    Map<String,Map<String, String>> hashMapListStream(List<String> redisKeys, List<String> hashKeys) throws Exception;


    /**
     * @author Crayon
     * @Description 修改各个车站最终设备告警等级结果
     * @date 2020/6/11 10:27 下午
     * @params []
     * @exception
     * @return java.lang.Boolean
     */
    Boolean updataAlarmLevelResult(Integer stationId) throws Exception;


}
