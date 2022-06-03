package com.jkk.aihome.strategy.state;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.DO.modestate.ModeOptionDO;
import com.jkk.aihome.entity.DO.modestate.ModeStateDO;
import com.jkk.aihome.entity.ModeOption;
import com.jkk.aihome.entity.VO.state.ModeStateDetailVO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.exception.IdNotFindException;
import com.jkk.aihome.hardware.request.AddStateRequest;
import com.jkk.aihome.hardware.request.ModeAddStateRequest;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.datainject.HardwareStateRepository;
import com.jkk.aihome.datainject.state.ModeOptionRepository;
import com.jkk.aihome.datainject.state.ModeStateRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ModeStrategy extends StateStrategy{
	private final ModeOptionRepository modeOptionRepository;
	private final ModeStateRepository modeStateRepository;

	protected ModeStrategy(HardwareStateRepository hardwareStateRepository, ModeOptionRepository modeOptionRepository, ModeStateRepository modeStateRepository) {
		super(hardwareStateRepository);
		this.modeOptionRepository = modeOptionRepository;
		this.modeStateRepository = modeStateRepository;
	}

	@Override
	public boolean isMatch(StateType stateType) {
		return stateType.equals(StateType.MODE);
	}

	@Override
	public StateDetailVO getDetailByStateId(String stateId) {
		ModeStateDetailVO modeStateDetailVO = new ModeStateDetailVO();
		super.copyHardwareStateDOProperties(modeStateDetailVO, stateId);

		ModeStateDO modeStateDO = modeStateRepository.findById(stateId).orElseThrow(() -> new IdNotFindException(stateId, "mode_state"));
		modeStateDetailVO.setType(StateType.MODE);
		modeStateDetailVO.setState(modeStateDO.getState());

		List<ModeOptionDO> modeOptionDOList = modeOptionRepository.findAllByStateId(stateId);
		Map<String, ModeOption> modeOptionMap = modeOptionDOList.stream()
				.collect(Collectors.toMap(ModeOptionDO::getModeValue, modeOptionDO -> {
					ModeOption modeOption = new ModeOption();
					BeanUtils.copyProperties(modeOptionDO, modeOption);
					return modeOption;
				}));

		modeStateDetailVO.setOptions(modeOptionMap);
		return modeStateDetailVO;
	}

	@Override
	public String getStringState(String stateId) {
		return modeStateRepository.findById(stateId).map(ModeStateDO::getState).orElseThrow(() -> new IdNotFindException(stateId, "mode_state"));
	}

	@Override
	public String getShowInHardwareIcon(String stateId) {
		String modeValue = modeStateRepository.findById(stateId).map(ModeStateDO::getState).orElseThrow(() -> new IdNotFindException(stateId, "mode_state"));
		return modeOptionRepository.findByStateIdAndModeValue(stateId, modeValue).getIcon();
	}

	@Transactional
	@Override
	public Boolean addState(AddStateRequest request) {
		ModeAddStateRequest modeAddStateRequest = (ModeAddStateRequest) request;
		String stateId = super.generateStateIdByDevId(modeAddStateRequest.getDevId());

		ModeStateDO modeStateDO = new ModeStateDO();
		modeStateDO.setStateId(stateId);
		modeStateDO.setState(modeAddStateRequest.getState());
		modeStateRepository.save(modeStateDO);


		List<ModeOptionDO> modeOptionDOList = modeAddStateRequest.getOptions().entrySet().stream()
				.map((o) -> {
					String modeValue = o.getKey();
					ModeOption option = o.getValue();

					ModeOptionDO modeOptionDO = new ModeOptionDO();
					modeOptionDO.setStateId(stateId);
					modeOptionDO.setModeValue(modeValue);
					BeanUtils.copyProperties(option, modeOptionDO);
					return modeOptionDO;
				}).collect(Collectors.toList());

		modeOptionDOList = modeOptionRepository.saveAll(modeOptionDOList);

		HardwareStateDO hardwareStateDO = super.buildHardwareStateDOFromAddStateRequest(modeAddStateRequest, stateId, StateType.MODE);
		hardwareStateRepository.save(hardwareStateDO);

		return modeOptionDOList.stream().allMatch(o -> o.getId() != null);
	}

	@Override
	public Boolean addState(String stateJson, String devId) {
		ModeAddStateRequest modeAddStateRequest = JSON.parseObject(stateJson, ModeAddStateRequest.class);
		modeAddStateRequest.setDevId(devId);
		return this.addState(modeAddStateRequest);
	}

	@Transactional
	@Override
	public Boolean deleteState(List<String> stateId) {
		return modeOptionRepository.removeAllByStateIdIn(stateId) != 0
				&& modeStateRepository.removeAllByStateIdIn(stateId) != 0;
	}

	@Override
	public void updateState(String stateId, Object state) {
		ModeStateDO modeStateDO = modeStateRepository.findById(stateId).orElseThrow(() -> new IdNotFindException(stateId, "mode_state"));
		modeStateDO.setState((String) state);
		modeStateRepository.save(modeStateDO);
	}

	@Override
	public Object convertStateToObject(String state) {
		return state;
	}
}
