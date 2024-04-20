package com.jkk.aihome.service.impl;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.entity.VO.StateVO;
import com.jkk.aihome.exception.IdNotFindException;
import com.jkk.aihome.hardware.request.AddStateRequest;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.datainject.HardwareStateRepository;
import com.jkk.aihome.service.IStateService;
import com.jkk.aihome.strategy.state.StateStrategy;
import com.jkk.aihome.strategy.state.StateStrategyManagement;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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
			stateVO.setReportTime(new Date(Long.parseLong(hardwareStateDO.getReportTime())));

			return stateVO;
		}).collect(Collectors.toList());
	}

	@Override
	public List<StateDetailVO> findStateDetailVOByDevId(String devId) {
		List<HardwareStateDO> hardwareStateDOList = hardwareStateRepository.findAllByDevIdOrderByReportTimeDesc(devId);

		return hardwareStateDOList.stream().map(hardwareStateDO ->
				this.findStateDetailVOByStateIdAndType(hardwareStateDO.getStateId(), StateType.of(hardwareStateDO.getType()))).collect(Collectors.toList());
	}

	@Override
	public StateDetailVO findStateDetailVOByStateIdAndType(String stateId, StateType stateType) {
		StateStrategy stateStrategy = stateStrategyManagement.getStateStrategyByStateType(stateType);
		return stateStrategy.getDetailByStateId(stateId);
	}

	@Override
	public StateDetailVO findStateDetailVOByStateId(String stateId) {
		StateStrategy stateStrategy = stateStrategyManagement.getStateStrategyByStateId(stateId);
		return stateStrategy.getDetailByStateId(stateId);
	}

	@Override
	public Boolean updateStateNameByStateId(String stateId, String name) {
		HardwareStateDO hardwareStateDO = hardwareStateRepository.findById(stateId).orElseThrow(() -> new IdNotFindException(stateId, "hardware_state"));
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

	@Override
	public Boolean addState(String stateJson, String devId) {
		AddStateRequest addStateRequest = JSON.parseObject(stateJson, AddStateRequest.class);
		return stateStrategyManagement.getStateStrategyByStateType(addStateRequest.getStateType()).addState(stateJson, devId);
	}

	@Transactional
	@Override
	public void updateState(String stateId, Object state) {
		HardwareStateDO hardwareStateDO = hardwareStateRepository.findById(stateId).orElseThrow(() -> new IdNotFindException(stateId, "hardware_state"));
		hardwareStateDO.setReportTime(String.valueOf(new Date().getTime()));
		hardwareStateRepository.save(hardwareStateDO);

		stateStrategyManagement.getStateStrategyByStateId(stateId).updateState(stateId, state);
	}

	@Override
	public void updateStateSting(String stateId, String state) {
		StateStrategy stateStrategy = stateStrategyManagement.getStateStrategyByStateId(stateId);
		stateStrategy.updateState(stateId, stateStrategy.convertStateToObject(state));
	}
}
