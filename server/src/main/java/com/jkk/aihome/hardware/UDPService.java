package com.jkk.aihome.hardware;
import java.util.Arrays;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.VO.MqttConfigVO;
import com.jkk.aihome.entity.config.MqttConfig;
import com.jkk.aihome.enums.TopicNameEnum;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.InitBinder;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.function.Function;
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

	private MqttConfigVO buildMqttConfigVO() {
		MqttConfigVO mqttConfigVO = new MqttConfigVO();
		String url = mqttConfig.getUrl().split("//")[1];
		if (url.contains("localhost") || url.contains("127.0.0.1")) {
			url = mqttConfig.getAddress();
		}
		String[] splitRes = url.split(":");
		mqttConfigVO.setAddress(splitRes[0]);
		mqttConfigVO.setPort(splitRes[1]);
		mqttConfigVO.setTopic(Arrays.stream(TopicNameEnum.values()).collect(Collectors.toMap(Enum::toString, TopicNameEnum::getName)));

		return mqttConfigVO;
	}
}
