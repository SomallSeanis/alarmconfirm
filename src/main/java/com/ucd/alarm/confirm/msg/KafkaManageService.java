package com.ucd.alarm.confirm.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.listener.MessageListenerContainer;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @ClassName: KafkaManageService
 * @Description: TODO
 * @Author: liuxin
 * @CreateDate: 2020/6/14 17:20
 * @Version 1.0
 * @Copyright: Copyright2018-2020 BJCJ Inc. All rights reserved.
 **/

@Service
public class KafkaManageService {

    @Resource
    private KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    public void stop() {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("alarm-confirm");
        listenerContainer.stop();
    }

    public void start() {
        MessageListenerContainer listenerContainer = kafkaListenerEndpointRegistry.getListenerContainer("alarm-confirm");
        listenerContainer.start();
    }
}