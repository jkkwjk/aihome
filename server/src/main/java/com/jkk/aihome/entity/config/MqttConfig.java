package com.jkk.aihome.entity.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("mqtt")
public class MqttConfig {
	private String url;

	private Integer keepAliveTime;

	private String address;
}
