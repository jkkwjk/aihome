package com.jkk.aihome.strategy.state;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.DO.ValueStateDO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.entity.VO.state.ValueStateDetailVO;
import com.jkk.aihome.entity.ValueConfig;
import com.jkk.aihome.exception.IdNotFindException;
import com.jkk.aihome.hardware.request.AddStateRequest;
import com.jkk.aihome.hardware.request.ValueAddStateRequest;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.datainject.HardwareStateRepository;
import com.jkk.aihome.datainject.state.ValueStateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class ValueStrategy extends StateStrategy{
	private final ValueStateRepository valueStateRepository;

	protected ValueStrategy(HardwareStateRepository hardwareStateRepository, ValueStateRepository valueStateRepository) {
		super(hardwareStateRepository);
		this.valueStateRepository = valueStateRepository;
	}

	@Override
	public boolean isMatch(StateType stateType) {
		return stateType.equals(StateType.VALUE);
	}

	@Override
	public StateDetailVO getDetailByStateId(String stateId) {
		ValueStateDetailVO valueStateDetailVO = new ValueStateDetailVO();
		super.copyHardwareStateDOProperties(valueStateDetailVO, stateId);

		ValueStateDO valueStateDO = valueStateRepository.findById(stateId).orElseThrow(() -> new IdNotFindException(stateId, "value_state"));
		BeanUtils.copyProperties(valueStateDO, valueStateDetailVO);
		ValueConfig valueConfig = new ValueConfig();
		BeanUtils.copyProperties(valueStateDO, valueConfig);

		valueStateDetailVO.setType(StateType.VALUE);
		valueStateDetailVO.setState(valueStateDO.getState());
		valueStateDetailVO.setConfig(valueConfig);
		return valueStateDetailVO;
	}

	@Override
	public String getStringState(String stateId) {
		return valueStateRepository.findById(stateId)
				.map(ValueStateDO::getState)
				.map(Objects::toString)
				.orElseThrow(() -> new IdNotFindException(stateId, "value_state"));
	}

	@Override
	public String getShowInHardwareIcon(String stateId) {
		return valueStateRepository.findById(stateId)
				.map(ValueStateDO::getIcon)
				.orElseThrow(() -> new IdNotFindException(stateId, "value_state"));
	}

	@Override
	@Transactional
	public Boolean addState(AddStateRequest request) {
		ValueAddStateRequest valueAddStateRequest = (ValueAddStateRequest) request;

		ValueStateDO valueStateDO = new ValueStateDO();
		BeanUtils.copyProperties(valueAddStateRequest, valueStateDO);
		String stateId = super.generateStateIdByDevId(valueAddStateRequest.getDevId());
		valueStateDO.setStateId(stateId);

		BeanUtils.copyProperties(valueAddStateRequest.getConfig(), valueStateDO);
		valueStateRepository.save(valueStateDO);

		HardwareStateDO hardwareStateDO = super.buildHardwareStateDOFromAddStateRequest(valueAddStateRequest, stateId, StateType.VALUE);
		hardwareStateRepository.save(hardwareStateDO);
		return true;
	}

	@Override
	public Boolean addState(String stateJson, String devId) {
		ValueAddStateRequest valueAddStateRequest = JSON.parseObject(stateJson, ValueAddStateRequest.class);
		valueAddStateRequest.setDevId(devId);
		return this.addState(valueAddStateRequest);
	}

	@Override
	public Boolean deleteState(List<String> stateId) {
		return valueStateRepository.removeAllByStateIdIn(stateId) != 0;
	}

	@Override
	public void updateState(String stateId, Object state) {
		ValueStateDO valueStateDO = valueStateRepository.findById(stateId).orElseThrow(() -> new IdNotFindException(stateId, "value_state"));
		valueStateDO.setState((Integer) state);
		valueStateRepository.save(valueStateDO);
	}

	@Override
	public Object convertStateToObject(String state) {
		return Integer.parseInt(state);
	}
}
