package com.jkk.aihome.datainject.impl;

import com.jkk.aihome.datainject.state.OnOffStateRepository;
import com.jkk.aihome.entity.DO.OnOffStateDO;
import com.jkk.aihome.repository.OnOffStateJpaRepository;
import org.quartz.Scheduler;
import org.springframework.stereotype.Repository;

@Repository
public class OnOffStateRepositoryImpl extends StateDataHolderImpl<OnOffStateDO, String> implements OnOffStateRepository {
    public OnOffStateRepositoryImpl(OnOffStateJpaRepository onOffStateJpaRepository, Scheduler scheduler) {
        super(onOffStateJpaRepository.findAll(), OnOffStateDO::getStateId, onOffStateJpaRepository, scheduler);
    }
}
