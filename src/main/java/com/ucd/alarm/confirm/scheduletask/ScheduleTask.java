package com.ucd.alarm.confirm.scheduletask;

import com.ucd.alarm.confirm.constants.BusinessConstants;
import com.ucd.alarm.confirm.service.AlarmRealTimeInfosService;
import com.ucd.alarm.confirm.service.AlarmRuleService;
import com.ucd.alarm.confirm.service.AlarmService;
import com.ucd.alarm.confirm.threadtask.AlarmTaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @ClassName: ScheduleTask
 * @Description: 告警信息定时任务
 * @Author: liuxin
 * @CreateDate: 2020/6/11 17:38
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Component
public class ScheduleTask {
    @Autowired
    AlarmService alarmService;
    @Autowired
    AlarmRealTimeInfosService alarmRealTimeInfosService;
    @Autowired
    AlarmRuleService alarmRuleService;

    /**
     * @return void
     * @throws
     * @author Crayon
     * @Description 车站id为1的定时任务
     * @date 2020/6/12 4:39 上午
     * @params []
     */
    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskOne() throws Exception {
        Integer stationId = BusinessConstants.StationId.JIN_CHUAN_LU_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwo() throws Exception {
        long startTime = System.currentTimeMillis();
        Integer stationId = BusinessConstants.StationId.DA_HE_GENG_STATION;
        boolean isGetCache = isGetCache(stationId);
        // true的话,代表可以执行
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("一个线程从执行到结束总共耗时为+" + (endTime - startTime) + "ms");

    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskThree() throws Exception {
        Integer stationId = BusinessConstants.StationId.HAI_TUN_LU_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskFourth() throws Exception {
        Integer stationId = BusinessConstants.StationId.XIAO_TUN_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskFifth() throws Exception {
        Integer stationId = BusinessConstants.StationId.JIN_DING_SHAN_BEI_LU_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskSixth() throws Exception {
        Integer stationId = BusinessConstants.StationId.SU_JIA_TANG_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskSeventh() throws Exception {
        Integer stationId = BusinessConstants.StationId.XIAO_CAI_YUAN_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskEighth() throws Exception {
        Integer stationId = BusinessConstants.StationId.HUO_CHE_BEI_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskNinth() throws Exception {
        Integer stationId = BusinessConstants.StationId.BAI_LONG_LU_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTenth() throws Exception {
        Integer stationId = BusinessConstants.StationId.DA_SHU_YING_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskEleventh() throws Exception {
        Integer stationId = BusinessConstants.StationId.JU_HUA_CUN_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwelfth() throws Exception {
        Integer stationId = BusinessConstants.StationId.JU_HUA_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskThirteenth() throws Exception {
        Integer stationId = BusinessConstants.StationId.HE_DIAN_YING_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskFourteenth() throws Exception {

        Integer stationId = BusinessConstants.StationId.NIU_JIE_ZHUANG_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskFifteenth() throws Exception {
        Integer stationId = BusinessConstants.StationId.ZHU_JIA_CUN_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskSixteenth() throws Exception {
        Integer stationId = BusinessConstants.StationId.YANG_FU_TOU_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskSeventeenth() throws Exception {
        Integer stationId = BusinessConstants.StationId.YU_YUAN_LU_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskEighteenth() throws Exception {
        Integer stationId = BusinessConstants.StationId.GUANG_WEI_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskNineteenth() throws Exception {
        Integer stationId = BusinessConstants.StationId.TA_MI_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwentieth() throws Exception {
        Integer stationId = BusinessConstants.StationId.DOU_NAN_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwentyFirst() throws Exception {
        Integer stationId = BusinessConstants.StationId.JIN_GUI_JIE_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwentySecond() throws Exception {
        Integer stationId = BusinessConstants.StationId.MEI_ZI_CUN_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTwentyThird() throws Exception {
        Integer stationId = BusinessConstants.StationId.GU_CHENG_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwentyFourth() throws Exception {
        Integer stationId = BusinessConstants.StationId.KE_LE_CUN_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwentyFifth() throws Exception {
        Integer stationId = BusinessConstants.StationId.XIANG_FENG_JIE_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwentySixth() throws Exception {
        Integer stationId = BusinessConstants.StationId.NIU_ROU_SHAN_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwentySeventh() throws Exception {
        Integer stationId = BusinessConstants.StationId.LIAN_DA_JIE_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwentyEighth() throws Exception {
        Integer stationId = BusinessConstants.StationId.WU_JIA_YING_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskTwentyNinth() throws Exception {
        Integer stationId = BusinessConstants.StationId.KUN_MING_HUO_CHE_NAN_STATION;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    //    @Scheduled(cron = "0/10 * * * * ?")
//    @Async("defaultThreadPool")
//    public void getRedisValueTaskThirty() throws Exception {
//        Integer stationId = BusinessConstants.StationId.CONTORL_CENTER;
//        boolean isGetCache = isGetCache(stationId);
//        if (isGetCache) {
//            boolean flag = alarmService.updataAlarmLevelResult(stationId);
//        }
//    }
    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskThirtyOne() throws Exception {
        Integer stationId = BusinessConstants.StationId.MA_XUN_ZHU_BIAN_DIAN_SUO;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskThirtyTwo() throws Exception {
        Integer stationId = BusinessConstants.StationId.DA_YANG_TIAN_CHE_LIANG_DUAN;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskThirtyThree() throws Exception {
        Integer stationId = BusinessConstants.StationId.HUO_CHE_BEI_CONTORL_CENTER;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskThirtyFour() throws Exception {
        Integer stationId = BusinessConstants.StationId.BAI_LONG_TAN_PART;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/10 * * * * ?")
    @Async("defaultThreadPool")
    public void getRedisValueTaskThirtyFive() throws Exception {
        Integer stationId = BusinessConstants.StationId.GUANG_WEI_PART;
        boolean isGetCache = isGetCache(stationId);
        if (isGetCache) {
            boolean flag = alarmService.updataAlarmLevelResult(stationId);
        }
    }

    @Scheduled(cron = "0/30 * * * * ?")
    public void getReloadAlarmInfoData() throws Exception {
        log.info("重新从库中加载AlarmInfos数据");
        alarmRealTimeInfosService.getAlarmLists();
    }


    @Scheduled(cron = "* 0/30 * * * ?")
    public void getReloadAlarmRuleData() throws Exception {
        log.info("重新从库中加载AlarmRule数据");
        alarmRuleService.getAlarmRuleLists();
    }

    private boolean isGetCache(Integer stationId) {
        if(AlarmTaskService.excRuleResultHashMap !=null && AlarmTaskService.excAlarmResultHashMap!=null){
            if (AlarmTaskService.excAlarmResultHashMap.get(stationId) == null || AlarmTaskService.excRuleResultHashMap.get(stationId) == null) {
                return false;
            }
            boolean isAlarmReady = AlarmTaskService.excAlarmResultHashMap.get(stationId);
            boolean isRuleReady = AlarmTaskService.excRuleResultHashMap.get(stationId);
            return isAlarmReady && isRuleReady;
        }
        return false;
    }
}
