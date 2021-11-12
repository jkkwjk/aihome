package com.jkk.aihome.service;

import com.jkk.aihome.entity.DO.HardwareDO;

import java.util.List;

public interface IHardwareService {
	List<HardwareDO> finAll();

	Integer add(HardwareDO hardwareDO);
}
