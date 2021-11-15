package com.jkk.aihome.service.impl;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.entity.VO.StateVO;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.repository.HardwareStateRepository;
import com.jkk.aihome.service.IStateService;
import com.jkk.aihome.strategy.state.StateStrategy;
import com.jkk.aihome.strategy.state.StateStrategyManagement;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StateServiceImpl implements IStateService {
	private final HardwareStateRepository hardwareStateRepository;
	private final StateStrategyManagement stateStrategyManagement;

	public StateServiceImpl(HardwareStateRepository hardwareStateRepository, StateStrategyManagement stateStrategyManagement) {
		this.hardwareStateRepository = hardwareStateRepository;
		this.stateStrategyManagement = stateStrategyManagement;
	}

	@Override
	public List<StateVO> findStateVOByDevId(String devId) {
		List<HardwareStateDO> hardwareStateDOList = hardwareStateRepository.findAllByDevIdOrderByReportTimeDesc(devId);

		return hardwareStateDOList.stream().map(hardwareStateDO -> {
			StateVO stateVO = new StateVO();
			BeanUtils.copyProperties(hardwareStateDO, stateVO);

			StateType stateType = StateType.of(hardwareStateDO.getType());
			String stateId = hardwareStateDO.getStateId();
			stateVO.setType(stateType);
			StateStrategy stateStrategy = stateStrategyManagement.getStateStrategyByStateType(stateType);
			stateVO.setState(stateStrategy.getStringState(stateId));
			stateVO.setIcon(stateStrategy.getShowInHardwareIcon(stateId));

			return stateVO;
		}).collect(Collectors.toList());
	}

	@Override
	public List<StateDetailVO> findStateDetailVOByDevId(String devId) {
		List<HardwareStateDO> hardwareStateDOList = hardwareStateRepository.findAllByDevIdOrderByReportTimeDesc(devId);

		return hardwareStateDOList.stream().map(hardwareStateDO -> {
			String stateId = hardwareStateDO.getStateId();
			StateType stateType = StateType.of(hardwareStateDO.getType());

			StateStrategy stateStrategy = stateStrategyManagement.getStateStrategyByStateType(stateType);
			return stateStrategy.getDetailByStateId(stateId);
		}).collect(Collectors.toList());
	}

	@Override
	public Boolean updateStateNameByStateId(String stateId, String name) {
		HardwareStateDO hardwareStateDO = hardwareStateRepository.findByStateId(stateId);
		hardwareStateDO.setName(name);
		return hardwareStateRepository.save(hardwareStateDO).getName().equals(name);
	}

	@Transactional
	@Override
	public void deleteAllStateByDevId(String devId) {
		List<HardwareStateDO> hardwareStateDO = hardwareStateRepository.findAllByDevIdOrderByReportTimeDesc(devId);

		hardwareStateDO.stream()
				.collect(Collectors.groupingBy(HardwareStateDO::getType,
						Collectors.mapping(HardwareStateDO::getStateId, Collectors.toList())))

				.forEach((stateType, stateIds) ->
						stateStrategyManagement.getStateStrategyByStateType(StateType.of(stateType)).deleteState(stateIds));

		hardwareStateRepository.removeAllByDevId(devId);
	}
}
