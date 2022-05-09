package com.jkk.aihome.strategy.state;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.DO.OnOffStateDO;
import com.jkk.aihome.entity.VO.state.OnOffStateDetailVO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.exception.IdNotFindException;
import com.jkk.aihome.hardware.request.AddStateRequest;
import com.jkk.aihome.hardware.request.OnOffAddStateRequest;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.datainject.HardwareStateRepository;
import com.jkk.aihome.datainject.state.OnOffStateRepository;
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

		OnOffStateDO onOffStateDO = onOffStateRepository.findById(stateId).orElseThrow(() -> new IdNotFindException(stateId, "on_off_state"));
		BeanUtils.copyProperties(onOffStateDO, onOffStateDetailVO);

		onOffStateDetailVO.setType(StateType.ON_OFF);
		onOffStateDetailVO.setState(onOffStateDO.getState());
		return onOffStateDetailVO;
	}

	@Override
	public String getStringState(String stateId) {
		return onOffStateRepository.findById(stateId)
				.map(OnOffStateDO::getState)
				.map(Object::toString)
				.orElseThrow(() -> new IdNotFindException(stateId, "on_off_state"));
	}

	@Override
	public String getShowInHardwareIcon(String stateId) {
		return onOffStateRepository.findById(stateId)
				.map(OnOffStateDO::getIcon)
				.orElseThrow(() -> new IdNotFindException(stateId, "on_off_state"));
	}

	@Override
	@Transactional
	public Boolean addState(AddStateRequest request) {
		OnOffAddStateRequest onOffAddStateRequest = (OnOffAddStateRequest) request;

		OnOffStateDO onOffStateDO = new OnOffStateDO();
		BeanUtils.copyProperties(onOffAddStateRequest, onOffStateDO);
		String stateId = super.generateStateIdByDevId(onOffAddStateRequest.getDevId());
		onOffStateDO.setStateId(stateId);
		onOffStateRepository.save(onOffStateDO);

		HardwareStateDO hardwareStateDO = super.buildHardwareStateDOFromAddStateRequest(onOffAddStateRequest, stateId, StateType.ON_OFF);
		hardwareStateRepository.save(hardwareStateDO);
		return true;
	}

	@Override
	public Boolean addState(String stateJson, String devId) {
		OnOffAddStateRequest onOffAddStateRequest = JSON.parseObject(stateJson, OnOffAddStateRequest.class);
		onOffAddStateRequest.setDevId(devId);
		return this.addState(onOffAddStateRequest);
	}

	@Override
	public Boolean deleteState(List<String> stateId) {
		return onOffStateRepository.removeAllByStateIdIn(stateId) != 0;
	}

	@Override
	public void updateState(String stateId, Object state) {
		OnOffStateDO onOffStateDO = onOffStateRepository.findById(stateId).orElseThrow(() -> new IdNotFindException(stateId, "on_off_state"));
		onOffStateDO.setState((Boolean) state);
		onOffStateRepository.save(onOffStateDO);
	}

	@Override
	public Object convertStateToObject(String state) {
		return Boolean.valueOf(state);
	}
}
