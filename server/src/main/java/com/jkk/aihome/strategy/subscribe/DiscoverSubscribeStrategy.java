package com.jkk.aihome.strategy.subscribe;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.hardware.request.GetDevIdRequest;
import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.hardware.response.GetDevIdResponse;
import com.jkk.aihome.util.IdUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class DiscoverSubscribeStrategy implements SubscribeStrategy {
	@Override
	public TopicNameEnum getMatchTopic() {
		return TopicNameEnum.DISCOVER;
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("topic = " + topic);
		System.out.println("id = " + message.getId());
		System.out.println("message = " + new String(message.getPayload()));
	}
}
