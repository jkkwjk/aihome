package com.jkk.aihome.datainject.impl;

import com.jkk.aihome.datainject.state.ModeOptionRepository;
import com.jkk.aihome.entity.DO.modestate.ModeOptionDO;
import com.jkk.aihome.repository.modestate.ModeOptionJpaRepository;
import org.quartz.Scheduler;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ModeOptionRepositoryImpl extends DataHolderImpl<ModeOptionDO, Integer> implements ModeOptionRepository {
	public ModeOptionRepositoryImpl(ModeOptionJpaRepository repository, Scheduler scheduler) {
		super(repository.findAll(), ModeOptionDO::getId, repository, scheduler);
	}

	@Override
	public List<ModeOptionDO> findAllByStateId(String stateId) {
		return super.findAll().stream()
				.filter(o -> o.getStateId().equals(stateId))
				.collect(Collectors.toList());
	}

	@Override
	public ModeOptionDO findByStateIdAndModeValue(String stateId, String modeValue) {
		return super.findAll().stream()
				.filter(o -> o.getStateId().equals(stateId))
				.filter(o -> o.getModeValue().equals(modeValue))
				.findFirst().orElse(null);
	}

	@Override
	public Integer removeAllByStateIdIn(Collection<String> stateId) {
		return Long.valueOf(super.findAll().stream()
				.filter(o -> stateId.contains(o.getStateId()))
				.peek(super::delete)
				.count()).intValue();
	}
}
