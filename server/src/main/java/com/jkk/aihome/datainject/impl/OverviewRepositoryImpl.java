package com.jkk.aihome.datainject.impl;

import com.jkk.aihome.datainject.OverviewRepository;
import com.jkk.aihome.entity.DO.OverviewDO;
import com.jkk.aihome.repository.OverviewJpaRepository;
import org.quartz.Scheduler;
import org.springframework.stereotype.Repository;

@Repository
public class OverviewRepositoryImpl extends DataHolderImpl<OverviewDO, Integer> implements OverviewRepository {
    public OverviewRepositoryImpl(OverviewJpaRepository overviewJpaRepository, Scheduler scheduler) {
        super(overviewJpaRepository.findAll(), OverviewDO::getId, overviewJpaRepository, scheduler);
    }
    @Override
    public OverviewDO findByStateId(String stateId) {
        return super.findAll().stream()
                .filter(overviewDO -> overviewDO.getStateId().equals(stateId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public OverviewDO findByAfterIdNull() {
        return super.findAll().stream()
                .filter(overviewDO -> overviewDO.getAfterId() == null)
                .findFirst()
                .orElse(null);
    }
}
