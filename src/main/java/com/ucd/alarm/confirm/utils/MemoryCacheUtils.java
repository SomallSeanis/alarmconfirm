package com.ucd.alarm.confirm.utils;

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

    /**容器*/
    private static final Map<String, Object> STATION_FIRST_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_SECOND_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_THIRD_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_FOURTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_FIFTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_SIXTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_SEVENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_EIGHTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_NINTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_ELEVENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWELFTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_THIRTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_FOURTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_FIFTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_SIXTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_SEVENTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_EIGHTEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_NINETEENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWENTIETH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWENTY_FIRST_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWENTY_SECOND_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWENTY_THIRD_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWENTY_FOURTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWENTY_FIFTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWENTY_SIXTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWENTY_SEVENTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWENTY_EIGHTH_CACHE_DATA = new ConcurrentHashMap<>();
    private static final Map<String, Object> STATION_TWENTY_NINTH_CACHE_DATA = new ConcurrentHashMap<>();


    /**
     * @throws
     * @Description: 获取集合大小
     * @author: wangweitao
     * @date: 2019/3/15 9:47
     */
    public static int getDataSize(Map<String, Object> map) {
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
    public static <T> void setData(String key,Object data,Map<String, Object> map) {
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
    public static void clear(String key,Map<String, Object> map) {
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
    public static void clearAll(Map<String, Object> map) {
        map.clear();
    }

    public static Map<String, Object> getMapByStationId(int stationId) {
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
}