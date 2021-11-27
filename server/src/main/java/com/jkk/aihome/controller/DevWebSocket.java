package com.jkk.aihome.controller;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.hardware.request.GetDevIdRequest;
import com.jkk.aihome.service.IMqttService;
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
import java.util.Map;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/ws/dev")
@Component
public class DevWebSocket {
	private static IMqttService mqttService;

	@Autowired
	public void setMqttService(IMqttService mqttService) {
		DevWebSocket.mqttService = mqttService;
	}

	private static SubscribeStrategyManagement subscribeStrategyManagement;

	@Autowired
	public void setSubscribeStrategyManagement(SubscribeStrategyManagement subscribeStrategyManagement) {
		DevWebSocket.subscribeStrategyManagement = subscribeStrategyManagement;
	}

	private final Set<Session> sessionSet = new HashSet<>();
	private final Map<String, GetDevIdRequest> concurrentMap = new ConcurrentHashMap<>();
	private final Observer observer = (o, arg) -> {
		GetDevIdRequest getDevIdRequest = (GetDevIdRequest) arg;
		concurrentMap.put(getDevIdRequest.getId(), getDevIdRequest);

		sessionSet.forEach(session -> {
			try {
				session.getBasicRemote().sendText(JSON.toJSONString(R.ok(concurrentMap.values())));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

	};

	@OnOpen
	public void onOpen(Session session) {
		sessionSet.add(session);
		subscribeStrategyManagement.getSubscribeStrategyByName(TopicNameEnum.DEV).addObserver(observer);
	}

	@OnMessage
	public void onMessage(Session session, String message) throws Exception{
		if (message.equals("discover")) {
			concurrentMap.clear();
			mqttService.startDiscover();
		}
	}

	@OnClose
	public void OnClose(Session session) {
		sessionSet.remove(session);
		subscribeStrategyManagement.getSubscribeStrategyByName(TopicNameEnum.DEV).deleteObserver(observer);
	}
}
