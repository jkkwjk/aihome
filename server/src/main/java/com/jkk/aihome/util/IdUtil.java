package com.jkk.aihome.util;

public class IdUtil {
	private final static String dict = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

	public static String generateDevId() {
		long time = System.nanoTime();
		int radix = dict.length() - 1;
		StringBuilder result = new StringBuilder();
		while(time > 0) {
			result.append(dict.charAt((int) (time % radix)));
			time /= radix;
		}
		return result.toString();
	}

	public static String getDevIdFromStateId(String stateId) {
		return stateId.split("-")[0];
	}

	public static String getStateIdFromDevIdAndId(String devId, Integer id) {
		return devId + "-" + id;
	}

	public static Integer getHardwareIdFromStateId(String stateId) {
		return Integer.parseInt(stateId.split("-")[1]);
	}
}
