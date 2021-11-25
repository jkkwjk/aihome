package com.jkk.aihome.strategy.subscribe;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.hardware.request.GetDevIdRequest;
import com.jkk.aihome.hardware.response.GetDevIdResponse;
import com.jkk.aihome.util.IdUtil;
import com.jkk.aihome.util.MessageUtil;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class DevSubscribeStrategy implements SubscribeStrategy {
	@Lazy
	@Autowired
	private MqttClient mqttClient;


	@Override
	public TopicNameEnum getMatchTopic() {
		return TopicNameEnum.DEV;
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		String data = new String(message.getPayload());
		if (MessageUtil.judgeSendByPC(data)) {
			GetDevIdRequest getDevIdRequest = JSON.parseObject(new String(data), GetDevIdRequest.class);

			GetDevIdResponse getDevIdResponse = new GetDevIdResponse();
			getDevIdResponse.setId(getDevIdRequest.getId());
			getDevIdResponse.setDevId(IdUtil.generateDevId());
			mqttClient.publish(topic, JSON.toJSONString(R.ok(getDevIdResponse)).getBytes(), 1, false);
		}
	}
}
