package com.ucd.alarm.confirm.domain;

import lombok.Data;

import java.util.Date;

/**
 * @ClassName: UserTestDO
 * @Description: 测试实体
 * @Author: Crayon
 * @CreateDate: 2020/6/5 4:55 下午
 * @Version 1.0
 * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
 **/
@Data
public class UserTestDO {

     Long Id;
     String guid;
     String name;
     int age;
     Date createTime;

}
