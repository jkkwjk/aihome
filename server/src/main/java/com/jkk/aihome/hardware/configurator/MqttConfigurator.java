package com.jkk.aihome.hardware.configurator;

import com.jkk.aihome.entity.config.MqttConfig;
import com.jkk.aihome.exception.MqttRuntimeException;
import com.jkk.aihome.strategy.subscribe.SubscribeStrategyManagement;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class MqttConfigurator {
	private final MqttConfig mqttConfig;

	private final SubscribeStrategyManagement subscribeStrategyManagement;

	public MqttConfigurator(MqttConfig mqttConfig, SubscribeStrategyManagement subscribeStrategyManagement) {
		this.mqttConfig = mqttConfig;
		this.subscribeStrategyManagement = subscribeStrategyManagement;
	}

	@Bean
	public MqttClient mqttClient() throws MqttException {
		MqttClient mqttClient = new MqttClient(mqttConfig.getUrl(), "server-" + System.getProperty("user.name"), new MemoryPersistence());
		MqttConnectOptions options = new MqttConnectOptions();
		options.setKeepAliveInterval(mqttConfig.getKeepAliveTime());
		options.setCleanSession(false);
		mqttClient.connect(options);
		subscribeStrategyManagement.getSubscribeStrategies().forEach(subscribeStrategy -> {
			try {
				mqttClient.subscribe(subscribeStrategy.getMatchTopic().getTopic(), subscribeStrategy);
			} catch (MqttException e) {
				throw new MqttRuntimeException(e);
			}
		});
		return mqttClient;
	}
}
