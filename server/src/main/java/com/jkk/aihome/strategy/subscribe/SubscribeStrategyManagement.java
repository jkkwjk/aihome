package com.jkk.aihome.strategy.subscribe;

import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.exception.MqttRuntimeException;
import lombok.Getter;
import lombok.SneakyThrows;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class SubscribeStrategyManagement implements ApplicationContextAware {
	@Getter
	private Collection<SubscribeStrategy> subscribeStrategies;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		subscribeStrategies = applicationContext.getBeansOfType(SubscribeStrategy.class).values();
	}

	public SubscribeStrategy getSubscribeStrategyByName(TopicNameEnum topicNameEnum) {
		return subscribeStrategies.stream().filter(strategy -> strategy.getMatchTopic().equals(topicNameEnum)).findFirst().orElse(null);
	}
}
