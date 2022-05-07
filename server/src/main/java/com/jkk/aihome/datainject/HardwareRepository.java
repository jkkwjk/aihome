package com.jkk.aihome.datainject;

import com.jkk.aihome.entity.DO.HardwareDO;

import java.util.List;

public interface HardwareRepository extends IDataHolder<HardwareDO, String> {
	List<HardwareDO> findAllSortByHeartTimeDesc();
}
