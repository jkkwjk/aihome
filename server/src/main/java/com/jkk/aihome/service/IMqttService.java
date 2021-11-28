package com.jkk.aihome.service;

public interface IMqttService {
	/**
	 * 发送允许所有设备提交获取设备id请求信息
	 */
	void startDiscover();

	/**
	 * 给指定的设备发送devid报文
	 * @param messageId 回应的设备消息id
	 */
	void sendDevId(String messageId);

	/**
	 * 给指定设备发送控制指令
	 * @param stateId 状态id
	 * @param state 想变成的状态
	 */
	void sendControlCMD(String stateId, String state);
}
