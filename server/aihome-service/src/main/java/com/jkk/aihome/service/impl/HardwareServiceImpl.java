package com.jkk.aihome.service.impl;

import com.jkk.aihome.entity.DO.HardwareDO;
import com.jkk.aihome.repository.HardwareRepository;
import com.jkk.aihome.service.IHardwareService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HardwareServiceImpl implements IHardwareService {
	private final HardwareRepository hardwareRepository;

	public HardwareServiceImpl(HardwareRepository hardwareRepository) {
		this.hardwareRepository = hardwareRepository;
	}

	@Override
	public List<HardwareDO> finAll() {
		return this.hardwareRepository.findAll();
	}

	@Override
	public Integer add(HardwareDO hardwareDO) {
		this.hardwareRepository.save(hardwareDO);
		return 1;
	}
}
