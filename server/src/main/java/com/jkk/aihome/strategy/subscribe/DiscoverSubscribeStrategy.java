package com.jkk.aihome.strategy.subscribe;

import com.jkk.aihome.enums.TopicNameEnum;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class DiscoverSubscribeStrategy implements SubscribeStrategy {
	@Override
	public TopicNameEnum getMatchTopic() {
		return TopicNameEnum.DISCOVER;
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println(topic);
		System.out.println(message.getId());
		System.out.println(new String(message.getPayload()));
	}
}
