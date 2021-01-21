package com.ucd.alarm.confirm.entity;

import lombok.Data;

@Data
public class StationAlarmSyncEntity {
    //要同步的车站id
    private Integer stationId;
    //要同步的updatesql
    private String updateSql;
    //要同步的insertSql
    private String insertSql;
}
