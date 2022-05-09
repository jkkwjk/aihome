package com.jkk.aihome.datainject.state;

import com.jkk.aihome.datainject.IDataHolder;

import java.io.Serializable;
import java.util.Collection;

public interface IStateDataHolder<T extends Serializable, ID> extends IDataHolder<T, ID> {
    Integer removeAllByStateIdIn(Collection<ID> stateId);
}
