package com.jkk.aihome.service.impl;

import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.hardware.response.GetDevIdResponse;
import com.jkk.aihome.hardware.response.IdResponse;
import com.jkk.aihome.service.IMqttService;
import com.jkk.aihome.strategy.subscribe.SubscribeStrategyManagement;
import com.jkk.aihome.util.IdUtil;
import com.jkk.aihome.util.MessageUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MqttServiceImpl implements IMqttService {
	private final MessageUtil messageUtil;

	public MqttServiceImpl(MessageUtil messageUtil) {
		this.messageUtil = messageUtil;
	}

	@Override
	public void startDiscover() {
		messageUtil.sendMessageOk(TopicNameEnum.DEV.getTopic(), new IdResponse("1"));
	}

	@Override
	public void sendDevId(String messageId) {
		GetDevIdResponse getDevIdResponse = new GetDevIdResponse();
		getDevIdResponse.setId(messageId);
		getDevIdResponse.setDevId(IdUtil.generateDevId());
		messageUtil.sendMessageOk(TopicNameEnum.DEV.getTopic(), getDevIdResponse);
	}
}
