package com.jkk.aihome.hardware;
import java.util.Arrays;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.hardware.response.MqttConfigResponse;
import com.jkk.aihome.entity.config.MqttConfig;
import com.jkk.aihome.enums.TopicNameEnum;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.stream.Collectors;

@Slf4j
@Service
@ConfigurationProperties("udp")
public class UDPService {
	@Setter
	private Integer port;
	@Setter
	private String connStr;

	private final MqttConfig mqttConfig;

	private static final Integer maxReceiveBytes = 4 * 1024;

	private DatagramSocket UDP;

	public UDPService(MqttConfig mqttConfig) {
		this.mqttConfig = mqttConfig;
	}

	@PostConstruct
	private void init() {
		new Thread(()->{
			byte[] buffer = new byte[maxReceiveBytes];
			DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
			try {
				UDP = new DatagramSocket(port);
				log.info("udp server started");
				while (true) {
					UDP.receive(packet);
					String data = new String(packet.getData(), packet.getOffset(), packet.getLength());
					if (connStr.equals(data)) {
						this.send(JSON.toJSONString(buildMqttConfigVO()), packet.getAddress(), packet.getPort());
					}
				}
			} catch (IOException e) {
				log.error("udp server error" + e.getMessage());
			}
		}).start();
	}

	private void send(String data, InetAddress address, int port) throws IOException {
		DatagramPacket sendPacket = new DatagramPacket(data.getBytes(), data.getBytes().length, address, port);
		UDP.send(sendPacket);
	}

	private MqttConfigResponse buildMqttConfigVO() {
		MqttConfigResponse mqttConfigResponse = new MqttConfigResponse();
		String[] splitRes = mqttConfig.getAddress().split(":");
		mqttConfigResponse.setAddress(splitRes[0]);
		mqttConfigResponse.setPort(splitRes[1]);
		mqttConfigResponse.setTopic(Arrays.stream(TopicNameEnum.values()).collect(Collectors.toMap(Enum::toString, TopicNameEnum::getName)));

		return mqttConfigResponse;
	}
}
