package com.jkk.aihome.service.impl;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.DO.OverviewDO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.exception.StateException;
import com.jkk.aihome.repository.HardwareStateRepository;
import com.jkk.aihome.repository.OverviewRepository;
import com.jkk.aihome.service.IOverviewService;
import com.jkk.aihome.service.IStateService;
import javafx.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class OverviewServiceImpl implements IOverviewService {
	private final IStateService stateService;
	private final OverviewRepository overviewRepository;
	private final HardwareStateRepository hardwareStateRepository;

	public OverviewServiceImpl(IStateService stateService, OverviewRepository overviewRepository, HardwareStateRepository hardwareStateRepository) {
		this.stateService = stateService;
		this.overviewRepository = overviewRepository;
		this.hardwareStateRepository = hardwareStateRepository;
	}

	@Override
	public List<StateDetailVO> getAddedOverview() {
		Map<Integer, OverviewDO> overviewDOIdMap = overviewRepository.findAll().stream()
				.collect(Collectors.toMap(OverviewDO::getId, Function.identity()));

		LinkedList<StateDetailVO> stateDetailVOListWithOrder = new LinkedList<>();
		if (overviewDOIdMap.size() == 0) {
			return stateDetailVOListWithOrder;
		}

		Integer firstId = overviewDOIdMap.keySet().iterator().next();
		OverviewDO first = overviewDOIdMap.get(firstId);
		OverviewDO last = first;
		stateDetailVOListWithOrder.add(stateService.findStateDetailVOByStateId(first.getStateId()));

		while (first.getBeforeId() != null) {
			first = overviewDOIdMap.get(first.getBeforeId());
			stateDetailVOListWithOrder.addFirst(stateService.findStateDetailVOByStateId(first.getStateId()));
		}
		while (last.getAfterId() != null) {
			last = overviewDOIdMap.get(last.getAfterId());
			stateDetailVOListWithOrder.addLast(stateService.findStateDetailVOByStateId(last.getStateId()));
		}

		return stateDetailVOListWithOrder;
	}

	@Override
	public List<StateDetailVO> getUnAddOverview() {
		Set<String> existOverviewStateId = overviewRepository.findAll().stream().map(OverviewDO::getStateId).collect(Collectors.toSet());

		return hardwareStateRepository.findAll().stream()
				.map(hardwareStateDO -> new Pair<>(StateType.of(hardwareStateDO.getType()), hardwareStateDO.getStateId()))
				.filter(statePair -> !existOverviewStateId.contains(statePair.getValue()))
				.map(statePair -> stateService.findStateDetailVOByStateIdAndType(statePair.getValue(), statePair.getKey()))
				.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public StateDetailVO addLastByStateId(String stateId) {
		OverviewDO lastOverviewDO = overviewRepository.findByAfterIdNull();

		OverviewDO newOverviewDO = new OverviewDO();
		newOverviewDO.setStateId(stateId);
		if (lastOverviewDO != null) {
			newOverviewDO.setBeforeId(lastOverviewDO.getId());
			newOverviewDO = overviewRepository.save(newOverviewDO);

			lastOverviewDO.setAfterId(newOverviewDO.getId());
			overviewRepository.save(lastOverviewDO);
		}else {
			newOverviewDO = overviewRepository.save(newOverviewDO);
		}

		return stateService.findStateDetailVOByStateId(stateId);
	}

	@Transactional
	@Override
	public void deleteOverviewByStateId(String stateId) {
		OverviewDO overviewDO = overviewRepository.findByStateId(stateId);
		if (overviewDO.getBeforeId() != null) {
			OverviewDO beforeOverviewDO = overviewRepository.findById(overviewDO.getBeforeId())
					.orElseThrow(() -> new StateException("overviewDO beforeId没有找到" + overviewDO));
			beforeOverviewDO.setAfterId(overviewDO.getAfterId());
			overviewRepository.save(beforeOverviewDO);
		}

		if (overviewDO.getAfterId() != null) {
			OverviewDO afterOverviewDO = overviewRepository.findById(overviewDO.getAfterId())
					.orElseThrow(() -> new StateException("overviewDO afterId没有找到" + overviewDO));
			afterOverviewDO.setBeforeId(overviewDO.getBeforeId());
			overviewRepository.save(afterOverviewDO);
		}

		overviewRepository.delete(overviewDO);
	}

	@Transactional
	@Override
	public void deleteAllOverviewByDevId(String devId) {
		hardwareStateRepository.findAllByDevIdOrderByReportTimeDesc(devId).stream()
				.map(HardwareStateDO::getStateId)
				.forEach(this::deleteOverviewByStateId);
	}
}
