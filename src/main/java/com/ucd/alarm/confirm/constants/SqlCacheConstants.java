package com.ucd.alarm.confirm.constants;

/**
 * @ClassName: SqlCacheConstants
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/8 12:51
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
public class SqlCacheConstants {

    private static final String SQL_1 = "select A.StationId,A.PointId,A.AlarmLevel_EntityId as AlarmLevel,A.AlarmRuleId,B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus = 0 and StationId = 1 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_2 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 2 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_3 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 3 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_4 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 4 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_5 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 5 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_6 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 6 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_7 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 7 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_8 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 8 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_9 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 9 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_10 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 10 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_11 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 11 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_12 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 12 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_13 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 13 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_14 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 14 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_15 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 15 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_16 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 16 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_17 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 17 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_18 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 18 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_19 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 19 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_20 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 20 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_21 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 21 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_22 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 22 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_23 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 23 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_24 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 24 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_25 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 25 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_26 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 26 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_27 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 27 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_28 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 28 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";
    private static final String SQL_29 = "select A.StationId, A.PointId,A.AlarmLevel_EntityId as AlarmLevel, A.AlarmRuleId, B.MaxTime from AlarmRealTimeInfoes as A INNER JOIN (select StationId, PointId,max(AlarmDateTime) as MaxTime from AlarmRealTimeInfoes WHERE AlarmStatus =0 and StationId = 29 group by StationId,PointId)B" +
            " on A.StationId=B.StationId AND A.PointId=B.PointId AND A.AlarmDateTime=B.MaxTime;";


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
            default:
                break;
        }
        return sql;
    }
}