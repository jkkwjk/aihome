package com.jkk.aihome.service;

import com.jkk.aihome.entity.VO.HardwareWithStateVO;

import java.util.List;

public interface IHardwareService {
	List<HardwareWithStateVO> findAllHardwiredAndStates();

	Boolean updateHardwareNameByDevId(String devId, String name);

	void deleteHardwareByDevId(String devId);
}
