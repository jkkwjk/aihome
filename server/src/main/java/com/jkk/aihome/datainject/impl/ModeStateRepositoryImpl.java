package com.jkk.aihome.datainject.impl;

import com.jkk.aihome.datainject.state.ModeStateRepository;
import com.jkk.aihome.datainject.state.OnOffStateRepository;
import com.jkk.aihome.entity.DO.OnOffStateDO;
import com.jkk.aihome.entity.DO.modestate.ModeStateDO;
import com.jkk.aihome.repository.OnOffStateJpaRepository;
import com.jkk.aihome.repository.modestate.ModeStateJpaRepository;
import org.quartz.Scheduler;
import org.springframework.stereotype.Repository;

@Repository
public class ModeStateRepositoryImpl extends StateDataHolderImpl<ModeStateDO, String> implements ModeStateRepository {
    public ModeStateRepositoryImpl(ModeStateJpaRepository modeStateJpaRepository, Scheduler scheduler) {
        super(modeStateJpaRepository.findAll(), ModeStateDO::getStateId, modeStateJpaRepository, scheduler);
    }
}
