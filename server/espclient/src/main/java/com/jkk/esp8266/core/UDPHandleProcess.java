package com.jkk.esp8266.core;

import com.jkk.esp8266.model.AppUser;
import com.jkk.esp8266.model.HandleResult;

import java.net.InetAddress;

public interface UDPHandleProcess {
	/**
	 * 当接收到UDP数据时调用该接口函数
	 * @param data 接收到的数据
	 * @param appUser 发送用户
	 * @return 链式调用. true: 允许其他handle进行处理, 反之拒绝
	 */
	HandleResult handle(String data, AppUser appUser);
}
