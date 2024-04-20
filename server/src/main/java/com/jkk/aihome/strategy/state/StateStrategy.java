package com.jkk.aihome.strategy.state;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.exception.IdNotFindException;
import com.jkk.aihome.hardware.request.AddStateRequest;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.datainject.HardwareStateRepository;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;


public abstract class StateStrategy {
	protected final HardwareStateRepository hardwareStateRepository;

	protected StateStrategy(HardwareStateRepository hardwareStateRepository) {
		this.hardwareStateRepository = hardwareStateRepository;
	}

	public abstract boolean isMatch(StateType stateType);

	public abstract StateDetailVO getDetailByStateId(String stateId);

	public abstract String getStringState(String stateId);

	public abstract String getShowInHardwareIcon(String stateId);

	public abstract Boolean addState(AddStateRequest request);

	public abstract Boolean addState(String stateJson, String devId);

	public abstract Boolean deleteState(List<String> stateId);

	public abstract void updateState(String stateId, Object state);

	public abstract Object convertStateToObject(String state);


	protected String generateStateIdByDevId(String devId) {
		return hardwareStateRepository.findFirstByDevIdOrderByStateIdDesc(devId)
				.map(HardwareStateDO::getStateId)
				.map(stateId -> stateId.split("-")[1])
				.map(Integer::valueOf)
				.map(o -> o + 1)
				.map(o -> devId + '-' + o)
				.orElse(devId + '-' + 0);
	}

	protected void copyHardwareStateDOProperties(StateDetailVO stateDetailVO, String stateId) {
		HardwareStateDO hardwareStateDO = hardwareStateRepository.findById(stateId).orElseThrow(() -> new IdNotFindException(stateId, "hardware_state"));
		BeanUtils.copyProperties(hardwareStateDO, stateDetailVO);
	}

	protected HardwareStateDO buildHardwareStateDOFromAddStateRequest(AddStateRequest addStateRequest, String stateId, StateType stateType) {
		HardwareStateDO hardwareStateDO = new HardwareStateDO();
		BeanUtils.copyProperties(addStateRequest, hardwareStateDO);
		hardwareStateDO.setStateId(stateId);
		hardwareStateDO.setType(stateType.getType());
		hardwareStateDO.setReportTime(String.valueOf(new Date().getTime()));
		return hardwareStateDO;
	}
}
