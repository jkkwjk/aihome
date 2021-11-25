package com.jkk.aihome.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MqttSendTest {
	@Autowired
	private MqttClient mqttClient;
	@Test
	public void test1() throws MqttException {
		mqttClient.publish("dev", "213".getBytes(), 1, false);
	}
}
