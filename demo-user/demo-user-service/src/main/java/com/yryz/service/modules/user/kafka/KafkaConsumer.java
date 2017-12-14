package com.yryz.service.modules.user.kafka;

import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.MessageListener;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/12/5
 * @description
 */
public class KafkaConsumer implements MessageListener<Integer, String> {
    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @Override
    public void onMessage(ConsumerRecord<Integer, String> record) {
        logger.info("KafkaConsumer get message from kafka: {}", JSON.toJSONString(record.value()));
    }
}
