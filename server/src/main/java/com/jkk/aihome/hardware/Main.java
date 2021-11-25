package com.jkk.aihome.hardware;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Main {
	public static void main(String[] args) throws MqttException {
		MqttClient mqttClient = new MqttClient("tcp://192.168.0.109:1883", "server-" + System.nanoTime(), new MemoryPersistence());

		MqttConnectOptions options = new MqttConnectOptions();
		options.setCleanSession(false);
		mqttClient.connect(options);

		mqttClient.subscribe("discover", 1);
	}
}
