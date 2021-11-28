package com.jkk.aihome.service.impl;
import com.jkk.aihome.hardware.HardwareState;

import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.hardware.response.ControlResponse;
import com.jkk.aihome.hardware.response.GetDevIdResponse;
import com.jkk.aihome.hardware.response.IdResponse;
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
		messageUtil.sendMessageOk(TopicNameEnum.DEV.getTopic(), new IdResponse("1"));
	}

	@Override
	public void sendDevId(String messageId) {
		GetDevIdResponse getDevIdResponse = new GetDevIdResponse();
		getDevIdResponse.setId(messageId);
		getDevIdResponse.setDevId(IdUtil.generateDevId());
		messageUtil.sendMessageOk(TopicNameEnum.DEV.getTopic(), getDevIdResponse);
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
