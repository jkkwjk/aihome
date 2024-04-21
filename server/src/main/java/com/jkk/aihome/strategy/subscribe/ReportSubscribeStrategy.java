package com.jkk.aihome.strategy.subscribe;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.aspect.ReceiveMsgNotPCSend;
import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.hardware.request.StateReportRequest;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.service.IMqttService;
import com.jkk.aihome.util.MessageUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class ReportSubscribeStrategy extends SubscribeStrategy {
	private final IHardwareService hardwareService;

	private final IMqttService mqttService;

	public ReportSubscribeStrategy(IHardwareService hardwareService, IMqttService mqttService) {
		this.hardwareService = hardwareService;
        this.mqttService = mqttService;
    }

	@Override
	public TopicNameEnum getMatchTopic() {
		return TopicNameEnum.REPORT;
	}

	@ReceiveMsgNotPCSend
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		StateReportRequest stateReportRequest = JSON.parseObject(message.toString(), StateReportRequest.class);
		if (hardwareService.reportStateProcess(stateReportRequest)) {
			// 通知设备状态变更
			this.setChanged();
			this.notifyObservers(stateReportRequest);
		}else {
			mqttService.deleteDevId(stateReportRequest.getDevId());
		}

	}
}
