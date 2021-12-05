package com.jkk.aihome.hardware.response;

import lombok.Data;

import java.util.Map;

@Data
public class MqttConfigResponse {
	private String address;

	private String port;

	private Map<String /*用途*/, String /*topic*/> topic;
}
