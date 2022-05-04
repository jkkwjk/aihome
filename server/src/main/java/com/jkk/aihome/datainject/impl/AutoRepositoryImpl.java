package com.jkk.aihome.datainject.impl;

import com.jkk.aihome.datainject.impl.DataHolderImpl;
import com.jkk.aihome.repository.AutoJpaRepository;
import com.jkk.aihome.entity.DO.AutoDO;
import com.jkk.aihome.datainject.AutoRepository;
import org.quartz.Scheduler;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AutoRepositoryImpl extends DataHolderImpl<AutoDO, Integer> implements AutoRepository {
	public AutoRepositoryImpl(AutoJpaRepository autoJpaRepository, Scheduler scheduler) {
		super(autoJpaRepository.findAll(), AutoDO::getId, autoJpaRepository, scheduler);
	}

	@Override
	public List<AutoDO> findAllByEnableTrue() {
		return super.findAll().stream()
				.filter(autoDO -> autoDO.getEnable().equals(true))
				.collect(Collectors.toList());
	}

	@Override
	public List<AutoDO> findByTypeOrderByIdDesc(Integer type) {
		return super.findAll().stream()
				.filter(autoDO -> autoDO.getType().equals(type))
				.sorted(Comparator.comparing(AutoDO::getId))
				.collect(Collectors.toList());
	}
}
