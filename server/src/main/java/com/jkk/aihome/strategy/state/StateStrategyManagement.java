package com.jkk.aihome.strategy.state;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.enums.StateExceptionEnum;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.exception.IdNotFindException;
import com.jkk.aihome.exception.StateException;
import com.jkk.aihome.repository.HardwareStateRepository;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class StateStrategyManagement implements ApplicationContextAware {
	private Collection<StateStrategy> stateStrategyList;
	private final HardwareStateRepository hardwareStateRepository;

	public StateStrategyManagement(HardwareStateRepository hardwareStateRepository) {
		this.hardwareStateRepository = hardwareStateRepository;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		stateStrategyList = applicationContext.getBeansOfType(StateStrategy.class).values();
	}

	public StateStrategy getStateStrategyByStateId(String stateId) {
		return this.getStateStrategyByStateType(StateType.of(hardwareStateRepository.findById(stateId).map(HardwareStateDO::getType).orElseThrow(() -> new IdNotFindException(stateId, "hardware_state"))));
	}

	public StateStrategy getStateStrategyByStateType(StateType stateType) {
		return stateStrategyList.stream()
				.filter(stateStrategy -> stateStrategy.isMatch(stateType))
				.findFirst().orElseThrow(() -> new StateException(StateExceptionEnum.NO_STATE_STRATEGY, stateType.getDescription()));
	}

}
