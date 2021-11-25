package com.jkk.aihome.hardware.core;

import com.jkk.aihome.hardware.model.AppUser;

import java.util.List;
import java.util.Observable;

public class ESP8266UDPServerAppObservable extends Observable {
	private String appId;
	private ESP8266UDPServer server;

	public ESP8266UDPServerAppObservable(String appId, ESP8266UDPServer server) {
		this.appId = appId;
		this.server = server;
	}

	public ESP8266UDPServer getNaive() {
		return server;
	}

	public boolean online(AppUser appUser) {
		return server.online(appId, appUser);
	}

	public void offline(AppUser appUser) {
		server.offline(appId, appUser);
	}

	public List<AppUser> getOnlineList() {
		return server.getOnlineList(appId);
	}

	public void addHandle(int index, UDPHandleProcess handleProcess) {
		server.addHandle(appId, index, handleProcess);
	}

	public void removeHandle(int index) {
		server.removeHandle(appId, index);
	}

	public void sendAndCheckOnline(String data, List<AppUser> checkUsers, Integer timeout) {
		server.sendAndCheckOnline(appId, data, checkUsers, timeout);
	}
}
