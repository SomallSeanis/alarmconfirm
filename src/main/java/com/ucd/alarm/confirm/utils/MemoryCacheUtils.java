package com.ucd.alarm.confirm.utils;

import com.ucd.alarm.confirm.entity.AlarmRealTimeInfos;
import com.ucd.alarm.confirm.entity.AlarmRule;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName: MemoryCacheUtils
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/7 19:04
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
public class MemoryCacheUtils {

    /**AlarmList容器*/
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_FIRST_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_SECOND_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_THIRD_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_FOURTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_FIFTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_SIXTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_SEVENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_EIGHTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_NINTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_ELEVENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWELFTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_THIRTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_FOURTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_FIFTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_SIXTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_SEVENTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_EIGHTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_NINETEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWENTIETH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWENTY_FIRST_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWENTY_SECOND_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWENTY_THIRD_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWENTY_FOURTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWENTY_FIFTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWENTY_SIXTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWENTY_SEVENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWENTY_EIGHTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, List<AlarmRealTimeInfos>> STATION_TWENTY_NINTH_CACHE_DATA = new ConcurrentHashMap<>();
    /**AlarmRule容器*/
    private static final Map<String, AlarmRule> STATION_FIRST_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_SECOND_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_THIRD_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_FOURTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_FIFTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_SIXTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_SEVENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_EIGHTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_NINTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_ELEVENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWELFTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_THIRTEENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_FOURTEENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_FIFTEENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_SIXTEENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_SEVENTEENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_EIGHTEENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_NINETEENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWENTIETH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWENTY_FIRST_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWENTY_SECOND_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWENTY_THIRD_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWENTY_FOURTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWENTY_FIFTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWENTY_SIXTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWENTY_SEVENTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWENTY_EIGHTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, AlarmRule> STATION_TWENTY_NINTH_RULE_CACHE_DATA = new ConcurrentHashMap<>();

    /**
     * @throws
     * @Description: 获取集合大小
     * @author: wangweitao
     * @date: 2019/3/15 9:47
     */
    public static int getDataSize(Map<String, List<AlarmRealTimeInfos>> listMap) {
        return listMap.size();
    }

    /**
     * @throws
     * @Description: 新增缓存数据
     * @param: [key, data, expire]
     * @return: void
     * @author: wangweitao
     * @date: 2019/3/15 9:47
     */
    public static <T> void setData(String key,List<AlarmRealTimeInfos> data,Map<String, List<AlarmRealTimeInfos>> map) {
        map.put(key, data);
    }

    /**
     * @throws
     * @Description: 根据key删除数据
     * @param: [key]
     * @return: void
     * @author: wangweitao
     * @date: 2019/3/15 9:47
     */
    public static void clear(String key,Map<String, List<AlarmRealTimeInfos>> map) {
        map.remove(key);
    }

    /**
     * @throws
     * @Description: 清空缓存容器
     * @param: []
     * @return: void
     * @author: wangweitao
     * @date: 2019/3/15 9:47
     */
    public static void clearAll(Map<String, List<AlarmRealTimeInfos>> map) {
        map.clear();
    }

    /**
     * @throws
     * @Description: 获取集合大小
     * @author: wangweitao
     * @date: 2019/3/15 9:47
     */
    public static int getRuleDataSize(Map<String, AlarmRule> map) {
        return map.size();
    }

    /**
     * @throws
     * @Description: 新增缓存数据
     * @param: [key, data, expire]
     * @return: void
     * @author: wangweitao
     * @date: 2019/3/15 9:47
     */
    public static <T> void setRuleData(String key,AlarmRule data,Map<String, AlarmRule> map) {
        map.put(key, data);
    }

    /**
     * @throws
     * @Description: 根据key删除数据
     * @param: [key]
     * @return: void
     * @author: wangweitao
     * @date: 2019/3/15 9:47
     */
    public static void clearRule(String key,Map<String, AlarmRule> map) {
        map.remove(key);
    }

    /**
     * @throws
     * @Description: 清空缓存容器
     * @param: []
     * @return: void
     * @author: wangweitao
     * @date: 2019/3/15 9:47
     */
    public static void clearAllRule(Map<String, AlarmRule> map) {
        map.clear();
    }

    public static Map<String,  List<AlarmRealTimeInfos>> getMapByStationId(int stationId) {
        switch (stationId) {
            case 1:
                return STATION_FIRST_CACHE_DATA;
            case 2:
                return STATION_SECOND_CACHE_DATA;
            case 3:
                return STATION_THIRD_CACHE_DATA;
            case 4:
                return STATION_FOURTH_CACHE_DATA;
            case 5:
                return STATION_FIFTH_CACHE_DATA;
            case 6:
                return STATION_SIXTH_CACHE_DATA;
            case 7:
                return STATION_SEVENTH_CACHE_DATA;
            case 8:
                return STATION_EIGHTH_CACHE_DATA;
            case 9:
                return STATION_NINTH_CACHE_DATA;
            case 10:
                return STATION_TENTH_CACHE_DATA;
            case 11:
                return STATION_ELEVENTH_CACHE_DATA;
            case 12:
                return STATION_TWELFTH_CACHE_DATA;
            case 13:
                return STATION_THIRTEENTH_CACHE_DATA;
            case 14:
                return STATION_FOURTEENTH_CACHE_DATA;
            case 15:
                return STATION_FIFTEENTH_CACHE_DATA;
            case 16:
                return STATION_SIXTEENTH_CACHE_DATA;
            case 17:
                return STATION_SEVENTEENTH_CACHE_DATA;
            case 18:
                return STATION_EIGHTEENTH_CACHE_DATA;
            case 19:
                return STATION_NINETEENTH_CACHE_DATA;
            case 20:
                return STATION_TWENTIETH_CACHE_DATA;
            case 21:
                return STATION_TWENTY_FIRST_CACHE_DATA;
            case 22:
                return STATION_TWENTY_SECOND_CACHE_DATA;
            case 23:
                return STATION_TWENTY_THIRD_CACHE_DATA;
            case 24:
                return STATION_TWENTY_FOURTH_CACHE_DATA;
            case 25:
                return STATION_TWENTY_FIFTH_CACHE_DATA;
            case 26:
                return STATION_TWENTY_SIXTH_CACHE_DATA;
            case 27:
                return STATION_TWENTY_SEVENTH_CACHE_DATA;
            case 28:
                return STATION_TWENTY_EIGHTH_CACHE_DATA;
            case 29:
                return STATION_TWENTY_NINTH_CACHE_DATA;
            default:
                break;
        }
        return new ConcurrentHashMap<>();
    }
    public static Map<String, AlarmRule> getRuleMapByStationId(int stationId) {
        switch (stationId) {
            case 1:
                return STATION_FIRST_RULE_CACHE_DATA;
            case 2:
                return STATION_SECOND_RULE_CACHE_DATA;
            case 3:
                return STATION_THIRD_RULE_CACHE_DATA;
            case 4:
                return STATION_FOURTH_RULE_CACHE_DATA;
            case 5:
                return STATION_FIFTH_RULE_CACHE_DATA;
            case 6:
                return STATION_SIXTH_RULE_CACHE_DATA;
            case 7:
                return STATION_SEVENTH_RULE_CACHE_DATA;
            case 8:
                return STATION_EIGHTH_RULE_CACHE_DATA;
            case 9:
                return STATION_NINTH_RULE_CACHE_DATA;
            case 10:
                return STATION_TENTH_RULE_CACHE_DATA;
            case 11:
                return STATION_ELEVENTH_RULE_CACHE_DATA;
            case 12:
                return STATION_TWELFTH_RULE_CACHE_DATA;
            case 13:
                return STATION_THIRTEENTH_RULE_CACHE_DATA;
            case 14:
                return STATION_FOURTEENTH_RULE_CACHE_DATA;
            case 15:
                return STATION_FIFTEENTH_RULE_CACHE_DATA;
            case 16:
                return STATION_SIXTEENTH_RULE_CACHE_DATA;
            case 17:
                return STATION_SEVENTEENTH_RULE_CACHE_DATA;
            case 18:
                return STATION_EIGHTEENTH_RULE_CACHE_DATA;
            case 19:
                return STATION_NINETEENTH_RULE_CACHE_DATA;
            case 20:
                return STATION_TWENTIETH_RULE_CACHE_DATA;
            case 21:
                return STATION_TWENTY_FIRST_RULE_CACHE_DATA;
            case 22:
                return STATION_TWENTY_SECOND_RULE_CACHE_DATA;
            case 23:
                return STATION_TWENTY_THIRD_RULE_CACHE_DATA;
            case 24:
                return STATION_TWENTY_FOURTH_RULE_CACHE_DATA;
            case 25:
                return STATION_TWENTY_FIFTH_RULE_CACHE_DATA;
            case 26:
                return STATION_TWENTY_SIXTH_RULE_CACHE_DATA;
            case 27:
                return STATION_TWENTY_SEVENTH_RULE_CACHE_DATA;
            case 28:
                return STATION_TWENTY_EIGHTH_RULE_CACHE_DATA;
            case 29:
                return STATION_TWENTY_NINTH_RULE_CACHE_DATA;
            default:
                break;
        }
        return new ConcurrentHashMap<>();
    }
}