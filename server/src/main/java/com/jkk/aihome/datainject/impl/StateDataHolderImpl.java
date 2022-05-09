package com.jkk.aihome.datainject.impl;

import com.jkk.aihome.datainject.state.IStateDataHolder;
import org.quartz.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.Collection;
import java.util.function.Function;

public class StateDataHolderImpl<T extends Serializable, ID> extends DataHolderImpl<T, ID> implements IStateDataHolder<T, ID> {
    public StateDataHolderImpl(Collection<T> data, Function<T, ID> idFunction, JpaRepository<T, ID> repository, Scheduler scheduler) {
        super(data, idFunction, repository, scheduler);
    }

    @Override
    public Integer removeAllByStateIdIn(Collection<ID> stateId) {
        stateId.forEach(super::deleteById);
        return stateId.size();
    }
}
