package com.jkk.aihome.service;

import com.jkk.aihome.entity.VO.HardwareWithStateVO;
import com.jkk.aihome.hardware.request.DiscoverRequest;
import com.jkk.aihome.hardware.request.StateReportRequest;

import java.util.List;

public interface IHardwareService {
	List<HardwareWithStateVO> findAllHardwiredAndStates();

	Boolean updateHardwareNameByDevId(String devId, String name);

	void deleteHardwareByDevId(String devId);

	Boolean addHardware(DiscoverRequest discoverRequest);

	Boolean reportStateProcess(StateReportRequest stateReportRequest);
}
