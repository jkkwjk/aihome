package com.jkk.aihome.configurator;

import com.jkk.aihome.entity.DTO.AutoDTO;
import org.python.core.PyDictionary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableScheduling
public class AutoBeanConfigurator {
	@Bean("autoTimer")
	public Map<Integer, AutoDTO> autoTimerJobMap() {
		return new HashMap<>();
	}
}
