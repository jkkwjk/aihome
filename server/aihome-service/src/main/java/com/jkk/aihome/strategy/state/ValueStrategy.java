package com.jkk.aihome.strategy.state;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.DO.ValueStateDO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.entity.VO.state.ValueStateDetailVO;
import com.jkk.aihome.entity.ValueConfig;
import com.jkk.aihome.entity.request.state.AddStateRequest;
import com.jkk.aihome.entity.request.state.ValueAddStateRequest;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.repository.HardwareStateRepository;
import com.jkk.aihome.repository.ValueStateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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

		ValueStateDO valueStateDO = valueStateRepository.findByStateId(stateId);
		BeanUtils.copyProperties(valueStateDO, valueStateDetailVO);

		valueStateDetailVO.setType(StateType.VALUE);
		valueStateDetailVO.setState(valueStateDO.getState().toString());
		return valueStateDetailVO;
	}

	@Override
	public String getStringState(String stateId) {
		return valueStateRepository.findByStateId(stateId).getState().toString();
	}

	@Override
	public String getShowInHardwareIcon(String stateId) {
		return valueStateRepository.findByStateId(stateId).getIcon();
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
		valueStateDO = valueStateRepository.save(valueStateDO);

		HardwareStateDO hardwareStateDO = super.buildHardwareStateDOFromAddStateRequest(valueAddStateRequest, stateId, StateType.VALUE);
		hardwareStateDO = hardwareStateRepository.save(hardwareStateDO);
		return valueStateDO.getId() != null && hardwareStateDO.getId() != null;
	}
}
