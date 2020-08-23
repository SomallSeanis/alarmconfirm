package com.ucd.alarm.confirm.constants;

/**
 * @ClassName: AlarmRuleSqlCacheConstants
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/8 12:51
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
//这个是告警规则的sql
public class AlarmRuleSqlCacheConstants {

    private static final String SQL_1 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=1;";
    private static final String SQL_2 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=2;";
    private static final String SQL_3 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=3;";
    private static final String SQL_4 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=4;";
    private static final String SQL_5 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=5;";
    private static final String SQL_6 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=6;";
    private static final String SQL_7 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=7;";
    private static final String SQL_8 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=8;";
    private static final String SQL_9 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=9;";
    private static final String SQL_10 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=10;";
    private static final String SQL_11 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=11;";
    private static final String SQL_12 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=12;";
    private static final String SQL_13 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=13;";
    private static final String SQL_14 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=14;";
    private static final String SQL_15 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=15;";
    private static final String SQL_16 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=16;";
    private static final String SQL_17 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=17;";
    private static final String SQL_18 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=18;";
    private static final String SQL_19 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=19;";
    private static final String SQL_20 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=20;";
    private static final String SQL_21 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=21;";
    private static final String SQL_22 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=22;";
    private static final String SQL_23 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=23;";
    private static final String SQL_24 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=24;";
    private static final String SQL_25 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=25;";
    private static final String SQL_26 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=26;";
    private static final String SQL_27 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=27;";
    private static final String SQL_28 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=28;";
    private static final String SQL_29 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=29;";
    private static final String SQL_30 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=30;";
    private static final String SQL_31 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=31;";
    private static final String SQL_32 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=39;";
    private static final String SQL_33 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=33;";
    private static final String SQL_34 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=34;";
    private static final String SQL_35 = "select A.EntityId as Id ,  A.AlarmType , A.StationId , A.PointId,A.HighLimit , A.LowLimit , C.[Order] as AlarmOrder from AlarmRule as A  LEFT JOIN AlarmLevels as C on C.EntityId = A.AlarmLevel_EntityId where  A.StationId=35;";


    public static String getSqlByStationId(int stationId) {
        String sql = null;
        switch (stationId) {
            case 1:
                sql = SQL_1;
                break;
            case 2:
                sql = SQL_2;
                break;
            case 3:
                sql = SQL_3;
                break;
            case 4:
                sql = SQL_4;
                break;
            case 5:
                sql = SQL_5;
                break;
            case 6:
                sql = SQL_6;
                break;
            case 7:
                sql = SQL_7;
                break;
            case 8:
                sql = SQL_8;
                break;
            case 9:
                sql = SQL_9;
                break;
            case 10:
                sql = SQL_10;
                break;
            case 11:
                sql = SQL_11;
                break;
            case 12:
                sql = SQL_12;
                break;
            case 13:
                sql = SQL_13;
                break;
            case 14:
                sql = SQL_14;
                break;
            case 15:
                sql = SQL_15;
                break;
            case 16:
                sql = SQL_16;
                break;
            case 17:
                sql = SQL_17;
                break;
            case 18:
                sql = SQL_18;
                break;
            case 19:
                sql = SQL_19;
                break;
            case 20:
                sql = SQL_20;
                break;
            case 21:
                sql = SQL_21;
                break;
            case 22:
                sql = SQL_22;
                break;
            case 23:
                sql = SQL_23;
                break;
            case 24:
                sql = SQL_24;
                break;
            case 25:
                sql = SQL_25;
                break;
            case 26:
                sql = SQL_26;
                break;
            case 27:
                sql = SQL_27;
                break;
            case 28:
                sql = SQL_28;
                break;
            case 29:
                sql = SQL_29;
                break;
            case 30:
                sql = SQL_30;
                break;
            case 31:
                sql = SQL_31;
                break;
            case 32:
                sql = SQL_32;
                break;
            case 33:
                sql = SQL_33;
                break;
            case 34:
                sql = SQL_34;
                break;
            case 35:
                sql = SQL_35;
                break;
            default:
                break;
        }
        return sql;
    }
}