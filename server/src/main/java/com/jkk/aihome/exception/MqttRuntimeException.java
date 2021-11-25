package com.jkk.aihome.exception;

import org.eclipse.paho.client.mqttv3.MqttException;

public class MqttRuntimeException extends RuntimeException {
	public MqttRuntimeException(MqttException mqttException) {
		super(mqttException.getMessage(), mqttException.getCause());
	}

}
