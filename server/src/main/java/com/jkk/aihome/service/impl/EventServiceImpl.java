package com.jkk.aihome.service.impl;

import com.jkk.aihome.entity.VO.StateVO;
import com.jkk.aihome.service.IEventService;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.util.IdUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class EventServiceImpl implements IEventService {
	private final IHardwareService hardwareService;

	public EventServiceImpl(IHardwareService hardwareService) {
		this.hardwareService = hardwareService;
	}

	@Override
	public List<String> findAllEvents() {
		return hardwareService.findAllHardwiredAndStates().stream()
				.flatMap(hardwareWithStateVO -> hardwareWithStateVO.getStates().stream().map(StateVO::getStateId))
				.map(IdUtil::generateStateEventId)
				.collect(Collectors.toList());
	}
}
