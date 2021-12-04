package com.jkk.aihome.service.impl;
import java.util.Date;

import com.jkk.aihome.entity.DO.HardwareDO;
import com.jkk.aihome.entity.VO.HardwareWithStateVO;
import com.jkk.aihome.hardware.request.DiscoverRequest;
import com.jkk.aihome.hardware.request.StateReportRequest;
import com.jkk.aihome.repository.HardwareRepository;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.service.IOverviewService;
import com.jkk.aihome.service.IStateService;
import com.jkk.aihome.util.IdUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HardwareServiceImpl implements IHardwareService {
	private final HardwareRepository hardwareRepository;
	private final IStateService stateService;
	private final IOverviewService overviewService;

	public HardwareServiceImpl(HardwareRepository hardwareRepository, IStateService stateService, IOverviewService overviewService) {
		this.hardwareRepository = hardwareRepository;
		this.stateService = stateService;
		this.overviewService = overviewService;
	}

	@Override
	public List<HardwareWithStateVO> findAllHardwiredAndStates() {
		List<HardwareDO> hardwareDOList = hardwareRepository.findAll(Sort.by(Sort.Direction.DESC, "heartTime"));

		return hardwareDOList.stream().map(hardwareDO -> {
			HardwareWithStateVO hardwareWithStateVO = new HardwareWithStateVO();
			BeanUtils.copyProperties(hardwareDO, hardwareWithStateVO);

			hardwareWithStateVO.setStates(stateService.findStateVOByDevId(hardwareDO.getDevId()));
			return hardwareWithStateVO;
		}).collect(Collectors.toList());
	}

	@Override
	public Boolean updateHardwareNameByDevId(String devId, String name) {
		HardwareDO hardwareDO = hardwareRepository.findByDevId(devId);
		hardwareDO.setName(name);
		return hardwareRepository.save(hardwareDO).getName().equals(name);
	}

	@Transactional
	@Override
	public void deleteHardwareByDevId(String devId) {
		overviewService.deleteAllOverviewByDevId(devId);
		stateService.deleteAllStateByDevId(devId);
		hardwareRepository.removeByDevId(devId);
	}

	@Transactional
	@Override
	public Boolean addHardware(DiscoverRequest discoverRequest) {
		HardwareDO hardwareDO = new HardwareDO();
		BeanUtils.copyProperties(discoverRequest, hardwareDO);
		hardwareDO.setDiscoverTime(new Date());
		hardwareDO.setHeartTime(new Date());
		hardwareDO = hardwareRepository.save(hardwareDO);

		// 添加状态
		boolean addStateResult = discoverRequest.getStates().stream()
				.map(stateJson -> stateService.addState(stateJson, discoverRequest.getDevId()))
				.allMatch((result) -> result.equals(true));

		return addStateResult && hardwareDO.getId() != null;
	}

	@Transactional
	@Override
	public Boolean reportStateProcess(StateReportRequest stateReportRequest) {
		HardwareDO hardwareDO = hardwareRepository.findByDevId(stateReportRequest.getDevId());
		if (hardwareDO == null) {
			return false;
		}
		hardwareDO.setHeartTime(new Date());
		hardwareRepository.save(hardwareDO);

		stateReportRequest.getStates().forEach(state -> {
			String stateId = IdUtil.getStateIdFromDevIdAndId(stateReportRequest.getDevId(), state.getId());
			stateService.updateState(stateId, state.getState());
		});

		return true;
	}
}
