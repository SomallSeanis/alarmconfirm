package com.ucd.alarm.confirm.msg;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @ClassName: AlarmMsgConsumer
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2019/11/15 17:18
 * @Version 1.0
 * @Copyright: Copyright2018-2019 BJCJ Inc. All rights reserved.
 **/
@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AlarmMsgConsumer {

    private final static String groupid = "alarmconfirm";

    /***
     * @author liuxin
     * @Description 监听topic,单条消费发送websocket，多节点不同消费者组
     * @date 2019/11/6 10:40
     * @params [record]
     * @exception
     * @return void
     */
    @KafkaListener(topics = "alarm",groupId = groupid)
    public void listenSubwayInfoWeb(ConsumerRecord<String, String> record) {
        consumerSubwayInfo(record);
    }

    /***
     * @author liuxin
     * @Description 消费列车相关消息
     * @date 2019/11/6 10:46
     * @params [record],[status:0.websocket,1:db]
     * @exception
     * @return void
     */
    public void consumerSubwayInfo(ConsumerRecord<String, String> record) {
        //java8加入的新类，可以有效防止空指针异常
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            this.dealSubwayInfo(message);
        }
    }

    /***
     * @author liuxin
     * @Description 列车实时数据处理
     * @date 2019/11/6 10:50
     * @params [message],[status:0.websocket,1:db]
     * @exception
     * @return void
     */
    public void dealSubwayInfo(Object message){

    }
}