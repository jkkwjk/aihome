package com.jkk.aihome.configurator;

import com.jkk.aihome.entity.DTO.AutoDTO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableScheduling
public class AutoBeanConfigurator {
	@Bean("autoTimer")
	public Map<Integer, AutoDTO> autoTimerJobMap() {
		return new ConcurrentHashMap<>();
	}

	@Bean("autoEvent")
	public Map<String, Set<AutoDTO>> autoEvent() {
		return new ConcurrentHashMap<>();
	}
}
