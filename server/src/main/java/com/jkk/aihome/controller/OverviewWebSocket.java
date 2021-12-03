package com.jkk.aihome.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jkk.aihome.entity.VO.HardwareWithStateVO;
import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.entity.VO.StateVO;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.service.IOverviewService;
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

@ServerEndpoint(value = "/ws/overview")
@Component
public class OverviewWebSocket {
	private static IOverviewService overviewService;

	@Autowired
	public void setHardwareService(IOverviewService overviewService) {
		OverviewWebSocket.overviewService = overviewService;
	}

	private static SubscribeStrategyManagement subscribeStrategyManagement;

	@Autowired
	public void setSubscribeStrategyManagement(SubscribeStrategyManagement subscribeStrategyManagement) {
		OverviewWebSocket.subscribeStrategyManagement = subscribeStrategyManagement;
	}

	private static ObjectMapper objectMapper;

	@Autowired
	public void setJsonMapper(ObjectMapper objectMapper) {
		OverviewWebSocket.objectMapper = objectMapper;
	}

	private final Set<Session> sessionSet = new HashSet<>();
	private final Observer observer = (o, arg) -> {
		List<StateDetailVO> stateDetailVOList = overviewService.getAddedOverview();

		sessionSet.forEach(session -> {
			try {
				session.getBasicRemote().sendText(objectMapper.writeValueAsString(R.ok(stateDetailVOList)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	};

	@OnOpen
	public void onOpen(Session session) {
		sessionSet.add(session);
		observer.update(null, null);
		subscribeStrategyManagement.getSubscribeStrategyByName(TopicNameEnum.REPORT).addObserver(observer);
	}

	@OnMessage
	public void onMessage(Session session, String message) throws Exception{
		if (message.equals("refresh")) {
			observer.update(null, null);
		}
	}

	@OnClose
	public void OnClose(Session session) {
		sessionSet.remove(session);
		subscribeStrategyManagement.getSubscribeStrategyByName(TopicNameEnum.REPORT).deleteObserver(observer);
	}
}
