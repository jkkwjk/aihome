package com.jkk.aihome.strategy.subscribe;

import com.jkk.aihome.aspect.ReceiveMsgNotPCSend;
import com.jkk.aihome.enums.TopicNameEnum;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class ControlSubscribeStrategy extends SubscribeStrategy {
	@Override
	public TopicNameEnum getMatchTopic() {
		return TopicNameEnum.CONTROL;
	}

	@ReceiveMsgNotPCSend
	@Override
	public void messageArrived(String topic, MqttMessage message) {

	}
}
