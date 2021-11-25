package com.jkk.aihome.hardware.config;

import com.jkk.aihome.exception.MqttRuntimeException;
import com.jkk.aihome.strategy.subscribe.SubscribeStrategy;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.BeansException;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;


@Configuration
@ConfigurationProperties("mqtt")
public class MqttConfigurator implements ApplicationContextAware {
	@Setter
	private String url;

	@Setter
	private Integer keepAliveTime;

	private Collection<SubscribeStrategy> subscribeStrategyCollections;

	@Bean
	public MqttClient mqttClient() throws MqttException {
		MqttClient mqttClient = new MqttClient(url, "server-" + System.nanoTime(), new MemoryPersistence());
		MqttConnectOptions options = new MqttConnectOptions();
		options.setKeepAliveInterval(keepAliveTime);
		options.setCleanSession(false);
//		mqttClient.connect(options);

		subscribeStrategyCollections.forEach(subscribeStrategy -> {
			try {
				mqttClient.subscribe(subscribeStrategy.getMatchTopic().getTopic(), subscribeStrategy);
			} catch (MqttException e) {
				throw new MqttRuntimeException(e);
			}
		});
		return mqttClient;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.subscribeStrategyCollections = applicationContext.getBeansOfType(SubscribeStrategy.class).values();
	}
}
