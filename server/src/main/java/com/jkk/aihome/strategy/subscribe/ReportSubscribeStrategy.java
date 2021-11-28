package com.jkk.aihome.strategy.subscribe;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.aspect.ReceiveMsgNotPCSend;
import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.hardware.request.StateReportRequest;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.service.IStateService;
import com.jkk.aihome.util.IdUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class ReportSubscribeStrategy extends SubscribeStrategy {
	private final IHardwareService hardwareService;

	public ReportSubscribeStrategy(IHardwareService hardwareService) {
		this.hardwareService = hardwareService;
	}

	@Override
	public TopicNameEnum getMatchTopic() {
		return TopicNameEnum.REPORT;
	}

	@ReceiveMsgNotPCSend
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		StateReportRequest stateReportRequest = JSON.parseObject(message.toString(), StateReportRequest.class);
		hardwareService.reportStateProcess(stateReportRequest);

		// 通知设备状态变更
		this.setChanged();
		this.notifyObservers();
	}
}
