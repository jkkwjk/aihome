package com.jkk.aihome.service.impl;
import java.util.Date;

import com.jkk.aihome.entity.DO.HardwareDO;
import com.jkk.aihome.entity.VO.HardwareWithStateVO;
import com.jkk.aihome.exception.IdNotFindException;
import com.jkk.aihome.hardware.request.DiscoverRequest;
import com.jkk.aihome.hardware.request.StateReportRequest;
import com.jkk.aihome.datainject.HardwareRepository;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.service.IMqttService;
import com.jkk.aihome.service.IOverviewService;
import com.jkk.aihome.service.IStateService;
import com.jkk.aihome.util.IdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HardwareServiceImpl implements IHardwareService {
	private final HardwareRepository hardwareRepository;
	private final IStateService stateService;
	private final IOverviewService overviewService;
	private final IMqttService mqttService;

	public HardwareServiceImpl(HardwareRepository hardwareRepository, IStateService stateService, IOverviewService overviewService, IMqttService mqttService) {
		this.hardwareRepository = hardwareRepository;
		this.stateService = stateService;
		this.overviewService = overviewService;
        this.mqttService = mqttService;
    }

	@Override
	public List<HardwareWithStateVO> findAllHardwiredAndStates() {
		List<HardwareDO> hardwareDOList = hardwareRepository.findAllSortByHeartTimeDesc();

		return hardwareDOList.stream().map(hardwareDO -> {
			HardwareWithStateVO hardwareWithStateVO = new HardwareWithStateVO();
			BeanUtils.copyProperties(hardwareDO, hardwareWithStateVO);
			hardwareWithStateVO.setDiscoverTime(new Date(Long.parseLong(hardwareDO.getDiscoverTime())));
			hardwareWithStateVO.setHeartTime(new Date(Long.parseLong(hardwareDO.getHeartTime())));
			hardwareWithStateVO.setStates(stateService.findStateVOByDevId(hardwareDO.getDevId()));
			return hardwareWithStateVO;
		}).collect(Collectors.toList());
	}

	@Override
	public Boolean updateHardwareNameByDevId(String devId, String name) {
		HardwareDO hardwareDO = hardwareRepository.findById(devId).orElseThrow(() -> new IdNotFindException(devId, "hardware"));
		hardwareDO.setName(name);
		return hardwareRepository.save(hardwareDO).getName().equals(name);
	}

	@Transactional
	@Override
	public void deleteHardwareByDevId(String devId) {
		overviewService.deleteAllOverviewByDevId(devId);
		stateService.deleteAllStateByDevId(devId);
		hardwareRepository.deleteById(devId);

		mqttService.deleteDevId(devId);
	}

	@Transactional
	@Override
	public Boolean addHardware(DiscoverRequest discoverRequest) {
		HardwareDO hardwareDO = new HardwareDO();
		BeanUtils.copyProperties(discoverRequest, hardwareDO);
		hardwareDO.setDiscoverTime(String.valueOf(new Date().getTime()));
		hardwareDO.setHeartTime(String.valueOf(new Date().getTime()));
		hardwareRepository.save(hardwareDO);

		// 添加状态
		return discoverRequest.getStates().stream()
				.map(stateJson -> stateService.addState(stateJson, discoverRequest.getDevId()))
				.allMatch((result) -> result.equals(true));
	}

	@Transactional
	@Override
	public Boolean reportStateProcess(StateReportRequest stateReportRequest) {
		String devId = stateReportRequest.getDevId();
		HardwareDO hardwareDO = hardwareRepository.findById(devId).orElse(null);
		if (hardwareDO == null) {
			return false;
		}
		hardwareDO.setHeartTime(String.valueOf(new Date().getTime()));
		hardwareRepository.save(hardwareDO);

		stateReportRequest.getStates().forEach(state -> {
			String stateId = IdUtil.getStateIdFromDevIdAndId(stateReportRequest.getDevId(), state.getId());
			stateService.updateState(stateId, state.getState());
		});

		return true;
	}
}
