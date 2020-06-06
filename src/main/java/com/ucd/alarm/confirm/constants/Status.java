package com.ucd.alarm.confirm.constants;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: Status
 * @Description: 所有状态相关-枚举类
 * @Author: Crayon
 * @CreateDate: 2020/6/5 4:41 下午
 * @Version 1.0
 * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
 **/
public abstract class Status {
    /**
     * 过期时间相关枚举
     */
    public static enum ExpireEnum{
        // todo 将设置 某些数据有效期为xx天 - 暂时不用
        UNREAD_MSG(30L, TimeUnit.DAYS)
        ;
        /**
         * 过期时间
         */
        private Long time;
        /**
         * 时间单位
         */
        private TimeUnit timeUnit;

        ExpireEnum(Long time, TimeUnit timeUnit) {
            this.time = time;
            this.timeUnit = timeUnit;
        }

        public Long getTime() {
            return time;
        }

        public TimeUnit getTimeUnit() {
            return timeUnit;
        }
    }
}
