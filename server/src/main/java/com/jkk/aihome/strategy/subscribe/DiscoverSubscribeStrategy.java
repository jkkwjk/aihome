package com.jkk.aihome.strategy.subscribe;

import com.jkk.aihome.aspect.ReceiveMsgNotPCSend;
import com.jkk.aihome.enums.TopicNameEnum;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class DiscoverSubscribeStrategy implements SubscribeStrategy {
	@Override
	public TopicNameEnum getMatchTopic() {
		return TopicNameEnum.DISCOVER;
	}

	@ReceiveMsgNotPCSend
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("topic = " + topic);
		System.out.println("id = " + message.getId());
		System.out.println("message = " + new String(message.getPayload()));
	}
}
