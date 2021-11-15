package com.jkk.aihome.strategy.state;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.DO.OnOffStateDO;
import com.jkk.aihome.entity.VO.state.OnOffStateDetailVO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.entity.request.state.AddStateRequest;
import com.jkk.aihome.entity.request.state.OnOffAddStateRequest;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.repository.HardwareStateRepository;
import com.jkk.aihome.repository.OnOffStateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OnOffStrategy extends StateStrategy {
	private final OnOffStateRepository onOffStateRepository;

	public OnOffStrategy(OnOffStateRepository onOffStateRepository, HardwareStateRepository hardwareStateRepository) {
		super(hardwareStateRepository);
		this.onOffStateRepository = onOffStateRepository;
	}

	@Override
	public boolean isMatch(StateType stateType) {
		return stateType.equals(StateType.ON_OFF);
	}

	@Override
	public StateDetailVO getDetailByStateId(String stateId) {
		OnOffStateDetailVO onOffStateDetailVO = new OnOffStateDetailVO();
		super.copyHardwareStateDOProperties(onOffStateDetailVO, stateId);

		OnOffStateDO onOffStateDO = onOffStateRepository.findByStateId(stateId);
		BeanUtils.copyProperties(onOffStateDO, onOffStateDetailVO);

		onOffStateDetailVO.setType(StateType.ON_OFF);
		onOffStateDetailVO.setState(onOffStateDO.getState().toString());
		return onOffStateDetailVO;
	}

	@Override
	public String getStringState(String stateId) {
		return onOffStateRepository.findByStateId(stateId).getState().toString();
	}

	@Override
	public String getShowInHardwareIcon(String stateId) {
		return onOffStateRepository.findByStateId(stateId).getIcon();
	}

	@Override
	@Transactional
	public Boolean addState(AddStateRequest request) {
		OnOffAddStateRequest onOffAddStateRequest = (OnOffAddStateRequest) request;

		OnOffStateDO onOffStateDO = new OnOffStateDO();
		BeanUtils.copyProperties(onOffAddStateRequest, onOffStateDO);
		String stateId = super.generateStateIdByDevId(onOffAddStateRequest.getDevId());
		onOffStateDO.setStateId(stateId);
		onOffStateDO = onOffStateRepository.save(onOffStateDO);

		HardwareStateDO hardwareStateDO = super.buildHardwareStateDOFromAddStateRequest(onOffAddStateRequest, stateId, StateType.ON_OFF);
		hardwareStateDO = hardwareStateRepository.save(hardwareStateDO);
		return onOffStateDO.getId() != null && hardwareStateDO.getId() != null;
	}

	@Override
	public Boolean deleteState(List<String> stateId) {
		return onOffStateRepository.removeAllByStateIdIn(stateId) != 0;
	}
}
