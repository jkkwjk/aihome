package com.jkk.aihome.service.impl;
import com.jkk.aihome.hardware.HardwareState;

import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.hardware.response.ControlResponse;
import com.jkk.aihome.hardware.response.GetDevIdResponse;
import com.jkk.aihome.hardware.response.IdResponse;
import com.jkk.aihome.hardware.response.dev.DevGenerateResponse;
import com.jkk.aihome.hardware.response.dev.DevRemoveResponse;
import com.jkk.aihome.hardware.response.dev.DevStartDiscoverResponse;
import com.jkk.aihome.service.IMqttService;
import com.jkk.aihome.strategy.state.StateStrategyManagement;
import com.jkk.aihome.util.IdUtil;
import com.jkk.aihome.util.MessageUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class MqttServiceImpl implements IMqttService {
	private final MessageUtil messageUtil;
	private final StateStrategyManagement stateStrategyManagement;

	public MqttServiceImpl(MessageUtil messageUtil, StateStrategyManagement stateStrategyManagement) {
		this.messageUtil = messageUtil;
		this.stateStrategyManagement = stateStrategyManagement;
	}

	@Override
	public void startDiscover() {
		messageUtil.sendMessageOk(TopicNameEnum.DEV.getTopic(), new DevStartDiscoverResponse());
	}

	@Override
	public void sendDevId(String mac) {
		messageUtil.sendMessageOk(TopicNameEnum.DEV.getTopic(), new DevGenerateResponse(mac, IdUtil.generateDevId()));
	}

	@Override
	public void deleteDevId(String devId) {
		messageUtil.sendMessageOk(TopicNameEnum.DEV.getTopic(), new DevRemoveResponse(devId));
	}

	@Override
	public void sendControlCMD(String stateId, String state) {
		ControlResponse controlResponse = new ControlResponse();
		String devId = IdUtil.getDevIdFromStateId(stateId);
		controlResponse.setId(devId + UUID.randomUUID());
		controlResponse.setDevId(devId);

		HardwareState hardwareState = new HardwareState();
		hardwareState.setId(IdUtil.getHardwareIdFromStateId(stateId));
		hardwareState.setState(stateStrategyManagement.getStateStrategyByStateId(stateId).convertStateToObject(state));
		controlResponse.setStates(hardwareState);

		messageUtil.sendMessageOk(TopicNameEnum.CONTROL.getTopic(), controlResponse);
	}
}
