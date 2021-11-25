package com.jkk.aihome.hardware.core;

import com.jkk.aihome.hardware.exception.AppNameRepeatException;
import com.jkk.aihome.hardware.model.AppUser;
import com.jkk.aihome.hardware.model.HandleResult;
import com.sun.istack.internal.NotNull;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

@Slf4j
public class ESP8266UDPServer {
	private static Integer maxReceiveBytes = 4 * 1024;

	private DatagramSocket UDP;

	// 在线机器列表
	private Map<String, Set<AppUser>> appUserMap;
	// 层级handleProcess处理
	private Map<String, List<UDPHandleProcess>> appHandleMap;
	// 需要测试机器是否在线
	private Map<String, Set<AppUser>> checkOnlineMap;

	public ESP8266UDPServer(int port, char spilt) {
		log.info("server starting");
		appUserMap = new HashMap<>();
		appHandleMap = new HashMap<>();
		checkOnlineMap = new HashMap<>();

		new Thread(()->{
			try {
				UDP = new DatagramSocket(port);
				log.info("server started");
				while (true) {
					String data = waitUtilReceive();

					if (this.handle(data, packet.getAddress(), packet.getPort())) {
						int pos = data.indexOf(spilt);
						if (pos == -1) {
							// 发送的数据包没有appId
							log.warn("Don't send appId, receiveData = {}", data);
							this.send(String.format("Please send appId%c[data]", spilt), packet.getAddress(), packet.getPort());

							continue;
						}

						String appId = data.substring(0, pos);
						List<UDPHandleProcess> handleProcessList = appHandleMap.get(appId);
						if (handleProcessList == null) {
							// appId 不存在
							log.warn("Can't find appId {}, receiveData = {}", appId, data);
							this.send("Can't find appId", packet.getAddress(), packet.getPort());

							continue;
						}

						this.online(appId, new AppUser(packet.getAddress(), packet.getPort()));

						int i = 0;
						String handleData = data.substring(pos + 1);
						while (i < handleProcessList.size()) {
							HandleResult result = handleProcessList.get(i).handle(handleData, new AppUser(packet.getAddress(), packet.getPort()));

							if (result.isContinue()) break;

							if (! handleData.equals(result.getNewData())) log.info("data was changed, {} in handle {}", handleData, i);

							handleData = result.getNewData();
							++i;
						}
					}

				}
			} catch (IOException e) {
				log.error("server stop" + e.getMessage());
			}
		}).start();
	}

	private void send(String data, InetAddress address, int port){
		try {
			DatagramPacket sendPacket = new DatagramPacket(data.getBytes(), data.getBytes().length, address, port);

			UDP.send(sendPacket);
		} catch (IOException e) {
			log.error(e.getMessage());
		}

	}

	public void send(String data, @NotNull AppUser appUser) {
		this.send(data, appUser.getAddress(), appUser.getPort());
	}

	public void send(String data, @NotNull List<AppUser> appUsers){
		appUsers.forEach(user -> this.send(data, user.getAddress(), user.getPort()));
	}

	public void sendAndCheckOnline(String appId, String data, List<AppUser> checkUsers, Integer timeout) {
		Set<AppUser> appUserWillCheck = this.checkOnlineMap.get(appId);

		for (AppUser checkUser : checkUsers) {
			if (appUserWillCheck.contains(checkUser)) {
				// 如果之前向该用户发送信息 且该用户未响应 则该用户下线
				appUserWillCheck.remove(checkUser);
				this.offline(appId, checkUser);
			}else {
				appUserWillCheck.add(checkUser);
				this.send(data, checkUser);
			}
		}

		new Thread(() -> {
			try {
				Thread.sleep(timeout);
				for (AppUser checkUser : checkUsers) {
					if (appUserWillCheck.contains(checkUser)) {
						appUserWillCheck.remove(checkUser);

						this.offline(appId, checkUser);
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	public ESP8266UDPServerAppObservable register(String appId, @NotNull List<UDPHandleProcess> handleProcessList) {
		this.register(appId);

		this.appHandleMap.put(appId, handleProcessList);
		return new ESP8266UDPServerAppObservable(appId, this);
	}

	public ESP8266UDPServerAppObservable register(String appId) {
		if (appHandleMap.containsKey(appId)) throw new AppNameRepeatException(appId);

		this.appHandleMap.put(appId, new ArrayList<>());
		this.appUserMap.put(appId, new HashSet<>());
		this.checkOnlineMap.put(appId, new HashSet<>());

		return new ESP8266UDPServerAppObservable(appId, this);
	}

	public void addHandle(String appId, int index, UDPHandleProcess handleProcess) {
		this.appHandleMap.get(appId).add(index, handleProcess);
	}

	public void removeHandle(String appId, int index) {
		this.appHandleMap.get(appId).remove(index);
	}

	/**
	 *
	 * @param appUser
	 * @return 是否为新用户
	 */
	public boolean online(String appId, AppUser appUser) {
		this.checkOnlineMap.get(appId).remove(appUser);
		return this.appUserMap.get(appId).add(appUser);
	}

	public void offline(String appId, AppUser appUser) {
		this.appUserMap.get(appId).remove(appUser);
	}

	public List<AppUser> getOnlineList(String appId) {
		return new ArrayList<>(this.appUserMap.get(appId));
	}


	private byte[] buffer = new byte[maxReceiveBytes];
	private DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

	private String waitUtilReceive() {
		String data = null;
		try {
			UDP.receive(packet);
			data = new String(packet.getData(), packet.getOffset(), packet.getLength());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	private Boolean handle(String data, InetAddress address, int port) {
		String ret = null;
		if (data.startsWith("AT+CIPSEND")) {
			ret = "OK";
		}else if (data.startsWith("AT")) {
			// 程序在透传模式发送
			ret = "+++";
		}

		if (ret != null) {
			this.send(ret, address, port);
		}
		return ret == null;
	}
}
