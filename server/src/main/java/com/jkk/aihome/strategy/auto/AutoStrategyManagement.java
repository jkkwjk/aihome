package com.jkk.aihome.strategy.auto;

import com.jkk.aihome.enums.AutoType;
import com.jkk.aihome.exception.IdNotFindException;
import com.jkk.aihome.datainject.AutoRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AutoStrategyManagement implements ApplicationContextAware {
	private Collection<AutoExecuteStrategy> autoExecuteStrategies;

	private final AutoRepository autoRepository;

	public AutoStrategyManagement(AutoRepository autoRepository) {
		this.autoRepository = autoRepository;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		autoExecuteStrategies = applicationContext.getBeansOfType(AutoExecuteStrategy.class).values();
	}

	public AutoExecuteStrategy getAutoStrategyByAutoType(AutoType autoType) {
		return autoExecuteStrategies.stream().filter(strategy -> strategy.isMatch(autoType)).findFirst().orElse(null);
	}

	public AutoExecuteStrategy getAutoStrategyByAutoId(Integer autoId) {
		AutoType autoType = AutoType.of(autoRepository.findById(autoId).orElseThrow(() -> new IdNotFindException(autoId, "auto")).getType());
		return this.getAutoStrategyByAutoType(autoType);
	}
}
