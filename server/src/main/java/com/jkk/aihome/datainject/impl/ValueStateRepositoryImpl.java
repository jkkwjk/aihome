package com.jkk.aihome.datainject.impl;

import com.jkk.aihome.datainject.state.ValueStateRepository;
import com.jkk.aihome.entity.DO.ValueStateDO;
import com.jkk.aihome.repository.ValueStateJpaRepository;
import org.quartz.Scheduler;
import org.springframework.stereotype.Repository;

@Repository
public class ValueStateRepositoryImpl extends StateDataHolderImpl<ValueStateDO, String> implements ValueStateRepository {
    public ValueStateRepositoryImpl(ValueStateJpaRepository valueStateJpaRepository, Scheduler scheduler) {
        super(valueStateJpaRepository.findAll(), ValueStateDO::getStateId, valueStateJpaRepository, scheduler);
    }
}
