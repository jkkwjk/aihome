package com.jkk.aihome.strategy.subscribe;

import com.jkk.aihome.enums.TopicNameEnum;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;

public interface SubscribeStrategy extends IMqttMessageListener {
	TopicNameEnum getMatchTopic();


}
