package com.jkk.aihome.service.impl;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.entity.DO.OverviewDO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.exception.IdNotFindException;
import com.jkk.aihome.datainject.HardwareStateRepository;
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
		independenceNodeByOverviewDO(overviewDO);
		overviewRepository.delete(overviewDO);
	}

	@Transactional
	@Override
	public void deleteAllOverviewByDevId(String devId) {
		hardwareStateRepository.findAllByDevIdOrderByReportTimeDesc(devId).stream()
				.map(HardwareStateDO::getStateId)
				.filter(stateId -> overviewRepository.findByStateId(stateId) != null)
				.forEach(this::deleteOverviewByStateId);
	}

	@Transactional
	@Override
	public Boolean reorderOverview(String stateId, String toStateId) {
		OverviewDO stateNode = overviewRepository.findByStateId(stateId);
		independenceNodeByOverviewDO(stateNode); // 删除被移动的节点

		if (toStateId != null) {
			// 插入到toStateId的前面
			OverviewDO toStateNode = overviewRepository.findByStateId(toStateId);
			if (toStateNode.getBeforeId() != null) {
				OverviewDO beforeToStateNode = overviewRepository.findById(toStateNode.getBeforeId())
						.orElseThrow(() -> new IdNotFindException(toStateNode.getBeforeId(), "overviewDO"));
				beforeToStateNode.setAfterId(stateNode.getId());
				overviewRepository.save(beforeToStateNode);
			}
			stateNode.setBeforeId(toStateNode.getBeforeId());

			toStateNode.setBeforeId(stateNode.getId());
			stateNode.setAfterId(toStateNode.getId());
			overviewRepository.save(toStateNode);
			overviewRepository.save(stateNode);
		}else {
			OverviewDO lastNode = overviewRepository.findByAfterIdNull();
			lastNode.setAfterId(stateNode.getId());
			stateNode.setAfterId(null);
			stateNode.setBeforeId(lastNode.getId());
			overviewRepository.save(lastNode);
			overviewRepository.save(stateNode);
		}

		return true;
	}

	/**
	 * 独立某个节点
	 * @param overviewDO
	 */
	private OverviewDO independenceNodeByOverviewDO(OverviewDO overviewDO) {
		if (overviewDO != null) {
			if (overviewDO.getBeforeId() != null) {
				OverviewDO beforeOverviewDO = overviewRepository.findById(overviewDO.getBeforeId())
						.orElseThrow(() -> new IdNotFindException(overviewDO.getBeforeId(), "overviewDO"));
				beforeOverviewDO.setAfterId(overviewDO.getAfterId());
				overviewRepository.save(beforeOverviewDO);
			}

			if (overviewDO.getAfterId() != null) {
				OverviewDO afterOverviewDO = overviewRepository.findById(overviewDO.getAfterId())
						.orElseThrow(() -> new IdNotFindException(overviewDO.getAfterId(), "overviewDO"));
				afterOverviewDO.setBeforeId(overviewDO.getBeforeId());
				overviewRepository.save(afterOverviewDO);
			}
		}

		return overviewDO;
	}

	/**
	 * 关联某个节点
	 * @param overviewDO
	 */
	private void relationNodeByOverviewDO(OverviewDO overviewDO) {
		Integer beforeId = overviewDO.getBeforeId();
		Integer afterId = overviewDO.getAfterId();

		if (beforeId != null) {
			OverviewDO beforeNode = overviewRepository.findById(beforeId)
					.orElseThrow(() -> new IdNotFindException(overviewDO.getBeforeId(), "overviewDO"));
			beforeNode.setAfterId(overviewDO.getId());
			overviewRepository.save(beforeNode);
		}

		if (afterId != null) {
			OverviewDO afterNode = overviewRepository.findById(afterId)
					.orElseThrow(() -> new IdNotFindException(overviewDO.getAfterId(), "overviewDO"));
			afterNode.setBeforeId(overviewDO.getId());
			overviewRepository.save(afterNode);
		}
	}
}
