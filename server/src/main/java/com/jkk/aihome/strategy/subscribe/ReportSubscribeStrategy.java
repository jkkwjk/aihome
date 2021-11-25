package com.jkk.aihome.strategy.subscribe;

import com.jkk.aihome.enums.TopicNameEnum;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class ReportSubscribeStrategy implements SubscribeStrategy {
	@Override
	public TopicNameEnum getMatchTopic() {
		return TopicNameEnum.REPORT;
	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {

	}
}
