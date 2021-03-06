package com.jkk.aihome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkk.aihome.entity.VO.HardwareWithStateVO;
import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.strategy.subscribe.SubscribeStrategyManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Observer;
import java.util.Set;

@ServerEndpoint(value = "/ws/hardware")
@Component
public class DiscoverWebSocket {
	private static IHardwareService hardwareService;

	@Autowired
	public void setHardwareService(IHardwareService hardwareService) {
		DiscoverWebSocket.hardwareService = hardwareService;
	}

	private static SubscribeStrategyManagement subscribeStrategyManagement;

	@Autowired
	public void setSubscribeStrategyManagement(SubscribeStrategyManagement subscribeStrategyManagement) {
		DiscoverWebSocket.subscribeStrategyManagement = subscribeStrategyManagement;
	}

	private static ObjectMapper objectMapper;

	@Autowired
	public void setJsonMapper(ObjectMapper objectMapper) {
		DiscoverWebSocket.objectMapper = objectMapper;
	}

	private final Set<Session> sessionSet = new HashSet<>();
	private final Observer observer = (o, arg) -> {
		List<HardwareWithStateVO> hardwareWithStateVOList = hardwareService.findAllHardwiredAndStates();

		sessionSet.forEach(session -> {
			try {
				session.getBasicRemote().sendText(objectMapper.writeValueAsString(R.ok(hardwareWithStateVOList)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	};

	@OnOpen
	public void onOpen(Session session) {
		sessionSet.add(session);
		observer.update(null, null);
		subscribeStrategyManagement.getSubscribeStrategyByName(TopicNameEnum.DISCOVER).addObserver(observer);
		subscribeStrategyManagement.getSubscribeStrategyByName(TopicNameEnum.REPORT).addObserver(observer);
	}

	@OnMessage
	public void onMessage(Session session, String message) throws Exception{

	}

	@OnClose
	public void OnClose(Session session) {
		sessionSet.remove(session);
		subscribeStrategyManagement.getSubscribeStrategyByName(TopicNameEnum.DISCOVER).deleteObserver(observer);
		subscribeStrategyManagement.getSubscribeStrategyByName(TopicNameEnum.REPORT).deleteObserver(observer);
	}
}
