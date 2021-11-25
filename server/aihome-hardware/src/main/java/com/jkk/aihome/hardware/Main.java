package com.jkk.aihome.hardware;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public class Main {
	public static void main(String[] args) throws MqttException {
		MqttClient mqttClient = new MqttClient("tcp://192.168.0.109:1883", "pc", new MemoryPersistence());

		mqttClient.setCallback(new MqttCallback() {
			@Override
			public void connectionLost(Throwable throwable) {
				System.out.println("connect lost");
			}

			@Override
			public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
				System.out.println(s);
				System.out.println(mqttMessage.toString());
				System.out.println(mqttMessage.getId());
			}

			@Override
			public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
				System.out.println(iMqttDeliveryToken.getResponse().toString());
			}
		});

		mqttClient.connect();
		mqttClient.subscribe("discover", 1);
	}
}
