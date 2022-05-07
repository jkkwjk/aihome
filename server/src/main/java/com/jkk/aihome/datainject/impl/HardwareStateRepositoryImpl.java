package com.jkk.aihome.datainject.impl;

import com.jkk.aihome.datainject.HardwareStateRepository;
import com.jkk.aihome.entity.DO.AutoDO;
import com.jkk.aihome.entity.DO.HardwareDO;
import com.jkk.aihome.entity.DO.HardwareStateDO;
import com.jkk.aihome.repository.HardwareStateJpaRepository;
import org.quartz.Scheduler;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class HardwareStateRepositoryImpl extends DataHolderImpl<HardwareStateDO, String> implements HardwareStateRepository {
	private final HardwareStateJpaRepository hardwareStateJpaRepository;

	public HardwareStateRepositoryImpl(HardwareStateJpaRepository hardwareStateJpaRepository, Scheduler scheduler) {
		super(hardwareStateJpaRepository.findAll(), HardwareStateDO::getStateId, hardwareStateJpaRepository, scheduler);
		this.hardwareStateJpaRepository = hardwareStateJpaRepository;
	}

	@Override
	public List<HardwareStateDO> findAllByDevIdOrderByReportTimeDesc(String devId) {
		return super.findAll().stream()
				.filter(hardwareStateDO -> hardwareStateDO.getDevId().equals(devId))
				.sorted(Comparator.comparing(HardwareStateDO::getReportTime).reversed())
				.collect(Collectors.toList());
	}

	@Override
	public Optional<HardwareStateDO> findFirstByDevIdOrderByStateIdDesc(String devId) {
		return super.findAll().stream()
				.filter(hardwareStateDO -> hardwareStateDO.getDevId().equals(devId))
				.max(Comparator.comparing(HardwareStateDO::getStateId));
	}

	@Override
	public Integer removeAllByDevId(String devId) {
		return Long.valueOf(super.findAll().stream()
				.filter(hardwareStateDO -> hardwareStateDO.getDevId().equals(devId))
				.peek(super::delete)
				.count()).intValue();
	}
}
