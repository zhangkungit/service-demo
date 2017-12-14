package com.yryz.service.modules.user.mq;

import com.alibaba.fastjson.JSON;
import com.yryz.common.mq.AbstractMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * MQ消费者： 创建用户
 * @author
 * 
 * @problem 上传Qr，注册IM的失败处理
 */
public class DemoMQListener extends AbstractMQListener {
	private static Logger logger = LoggerFactory.getLogger(DemoMQListener.class);

	@Override
	public void handleAsynMessage(Map<String, String> data) {
		logger.info("get splitOrder message: {}", JSON.toJSONString(data));
		logger.info("handleAsynMessage.....................");
	}
	

}
