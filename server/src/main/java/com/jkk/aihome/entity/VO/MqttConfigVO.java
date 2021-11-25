package com.jkk.aihome.entity.VO;

import lombok.Data;

import java.util.Map;

@Data
public class MqttConfigVO {
	private String address;

	private String port;

	private Map<String /*用途*/, String /*topic*/> topic;
}
