package com.jkk.aihome.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.exception.MqttRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageUtil {
	@Lazy
	@Autowired
	private MqttClient mqttClient;


	public void sendMessageOk(String topic, Object message) {
		try {
			mqttClient.publish(topic, JSON.toJSONString(R.ok(message)).getBytes(), 1, false);
		} catch (MqttException e) {
			throw new MqttRuntimeException(e);
		}
	}

	public void sendMessageError(String topic, String message) {
		try {
			mqttClient.publish(topic, JSON.toJSONString(R.error(message)).getBytes(), 1, false);
		} catch (MqttException e) {
			throw new MqttRuntimeException(e);
		}
	}

	public static Boolean judgeSendByPC(String message) {
		JSONObject jsonObject = JSON.parseObject(message);
		return jsonObject.get("code") != null;
	}
}
