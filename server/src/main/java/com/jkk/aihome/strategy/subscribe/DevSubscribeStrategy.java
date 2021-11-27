package com.jkk.aihome.strategy.subscribe;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.aspect.ReceiveMsgNotPCSend;
import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.hardware.request.GetDevIdRequest;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DevSubscribeStrategy extends SubscribeStrategy {
	@Lazy
	@Autowired
	private MqttClient mqttClient;


	@Override
	public TopicNameEnum getMatchTopic() {
		return TopicNameEnum.DEV;
	}

	@Override
	@ReceiveMsgNotPCSend
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		this.setChanged();
		this.notifyObservers(JSON.parseObject(String.valueOf(message), GetDevIdRequest.class));
	}
}
