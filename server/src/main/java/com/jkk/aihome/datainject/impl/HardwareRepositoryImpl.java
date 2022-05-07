package com.jkk.aihome.datainject.impl;

import com.jkk.aihome.datainject.HardwareRepository;
import com.jkk.aihome.entity.DO.HardwareDO;
import com.jkk.aihome.repository.HardwareJpaRepository;
import org.quartz.Scheduler;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class HardwareRepositoryImpl extends DataHolderImpl<HardwareDO, String> implements HardwareRepository {
	public HardwareRepositoryImpl(HardwareJpaRepository hardwareJpaRepository, Scheduler scheduler) {
		super(hardwareJpaRepository.findAll(), HardwareDO::getDevId, hardwareJpaRepository, scheduler);
	}

	@Override
	public List<HardwareDO> findAllSortByHeartTimeDesc() {
		return super.findAll().stream()
				.sorted(Comparator.comparing(HardwareDO::getHeartTime).reversed())
				.collect(Collectors.toList());
	}
}
