package com.ucd.alarm.confirm;

import com.ucd.alarm.confirm.service.AlarmService;
import com.ucd.alarm.confirm.task.AlarmTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: AlarmTest
 * @Description: TODO
 * @Author: Crayon
 * @CreateDate: 2020/6/11 2:56 下午
 * @Version 1.0
 * @Copyright: Copyright©2018-2020 BJCJ Inc. All rights reserved.
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlarmTest {

    @Autowired
    AlarmTaskService alarmTaskService;
    @Autowired
    AlarmService alarmService;

    @Test
    public void parallelStreamTest() {
        List<String> keys = new ArrayList<>();
        List<String> hashKeys = new ArrayList<>();
        List<Map<String, String>> hashMapListStream = new ArrayList<Map<String, String>>();
        List<Map<String, String>> hashMapList = new ArrayList<Map<String, String>>();
        keys.add("db0");
/*
        hashKeys.add(key1);
        hashKeys.add(key2);
*/
        hashKeys.add("2_36171");
        hashKeys.add("2_4690800");
        hashKeys.add("2_13050000");
        hashKeys.add("2_6904900");
        hashKeys.add("2_7510300");
        hashKeys.add("2_1887400");
        hashKeys.add("2_6875900");
        hashKeys.add("2_54016");

        List<Map<String, String>> mapList = alarmService.hashMapListStream(keys, hashKeys);


        // parallelStream数据测试
        List<String> list1 = new ArrayList<>();
        List<String> list2 = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list1.add(String.valueOf(i));
        }
        list1.parallelStream().forEachOrdered(p -> {
            list2.add(p);
        });


        System.out.println("大数据量处理parallelStream" + list2.toString());
        System.out.println("大数据量处理parallelStream获取的数据" + list2.size());
    }


}
