package com.jkk.aihome.datainject;

import com.jkk.aihome.datainject.IDataHolder;
import com.jkk.aihome.entity.DO.AutoDO;

import java.util.List;

public interface AutoRepository extends IDataHolder<AutoDO, Integer> {
	List<AutoDO> findAllByEnableTrue();

	List<AutoDO> findByTypeOrderByIdDesc(Integer type);
}
