package com.jkk.aihome.service.impl;

import com.jkk.aihome.entity.DO.HardwareDO;
import com.jkk.aihome.entity.VO.HardwareWithStateVO;
import com.jkk.aihome.repository.HardwareRepository;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.service.IStateService;
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

	public HardwareServiceImpl(HardwareRepository hardwareRepository, IStateService stateService) {
		this.hardwareRepository = hardwareRepository;
		this.stateService = stateService;
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
		hardwareRepository.removeByDevId(devId);
		stateService.deleteAllStateByDevId(devId);
	}
}
