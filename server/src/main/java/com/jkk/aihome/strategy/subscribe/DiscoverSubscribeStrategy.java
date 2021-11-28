package com.jkk.aihome.strategy.subscribe;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.aspect.ReceiveMsgNotPCSend;
import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.hardware.request.DiscoverRequest;
import com.jkk.aihome.hardware.response.IdResponse;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.util.MessageUtil;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Component;

@Component
public class DiscoverSubscribeStrategy extends SubscribeStrategy {
	private final IHardwareService hardwareService;
	private final MessageUtil messageUtil;

	public DiscoverSubscribeStrategy(IHardwareService hardwareService, MessageUtil messageUtil) {
		this.hardwareService = hardwareService;
		this.messageUtil = messageUtil;
	}

	@Override
	public TopicNameEnum getMatchTopic() {
		return TopicNameEnum.DISCOVER;
	}

	@ReceiveMsgNotPCSend
	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		System.out.println("topic = " + topic);
		System.out.println("id = " + message.getId());
		System.out.println("message = " + new String(message.getPayload()));

		DiscoverRequest discoverRequest = JSON.parseObject(message.toString(), DiscoverRequest.class);
		if (hardwareService.addHardware(discoverRequest)) {
//			messageUtil.sendMessageOk(TopicNameEnum.DISCOVER.getTopic(), new IdResponse(discoverRequest.getId()));

			// 告知消费者新加了一个硬件
			this.setChanged();
			this.notifyObservers();
		}

	}
}
