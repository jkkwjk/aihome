package com.jkk.aihome.strategy.state;
import java.util.Date;
import java.util.HashMap;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.DO.modestate.ModeOptionDO;
import com.jkk.aihome.entity.DO.modestate.ModeStateDO;
import com.jkk.aihome.entity.ModeOption;
import com.jkk.aihome.entity.VO.state.ModeStateDetailVO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.entity.request.state.AddStateRequest;
import com.jkk.aihome.entity.request.state.ModeAddStateRequest;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.repository.HardwareStateRepository;
import com.jkk.aihome.repository.modestate.ModeOptionRepository;
import com.jkk.aihome.repository.modestate.ModeStateRepository;
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

		ModeStateDO modeStateDO = modeStateRepository.findByStateId(stateId);
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
		String modeValue = modeStateRepository.findByStateId(stateId).getState(); // 内部显示数值
		return modeOptionRepository.findByStateIdAndModeValue(stateId, modeValue).getModeText();
	}

	@Override
	public String getShowInHardwareIcon(String stateId) {
		String modeValue = modeStateRepository.findByStateId(stateId).getState();
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
		modeStateDO = modeStateRepository.save(modeStateDO);


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
		hardwareStateDO = hardwareStateRepository.save(hardwareStateDO);

		return modeStateDO.getId() != null && hardwareStateDO.getId() != null
				&& modeOptionDOList.stream().allMatch(o -> o.getId() != null);
	}

	@Transactional
	@Override
	public Boolean deleteState(List<String> stateId) {
		return modeOptionRepository.removeAllByStateIdIn(stateId) != 0
				&& modeStateRepository.removeAllByStateIdIn(stateId) != 0;
	}
}
