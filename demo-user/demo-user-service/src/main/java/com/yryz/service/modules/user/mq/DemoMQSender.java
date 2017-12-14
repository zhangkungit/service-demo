package com.yryz.service.modules.user.mq;

import com.yryz.common.mq.AbstractMQSender;
import org.apache.commons.collections.MapUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class DemoMQSender extends AbstractMQSender {

	//队列名称
	//拆单消息
	private static final String MQ_QUEUE = "demoservice.user.queue";

	// session名称
	// 基础直连交换机
	private static final int APP_DIRECT_COMMON = 1;
	// 扇形交换机
	private static final int APP_FANOUT_ = 2;

	@Autowired
	private RabbitTemplate directTemplate;

//	@Autowired
//	private RabbitTemplate commonTemplate;


	@Override
	public RabbitTemplate checkTemplate(int app) {
		switch (app) {
		case APP_DIRECT_COMMON:
			return directTemplate;
//			return null;
		case APP_FANOUT_:
//			return commonTemplate;
			return null;
		default:
			return null;
		}
	}

	public void sendSplitOrderMQ(Map<String, String> params) {
		if (MapUtils.isNotEmpty(params)) {
			this.send(APP_DIRECT_COMMON, MQ_QUEUE, params);
		}
	}

}
