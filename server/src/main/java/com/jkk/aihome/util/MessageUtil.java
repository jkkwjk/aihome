package com.jkk.aihome.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class MessageUtil {
	public static Boolean judgeSendByPC(String message) {
		JSONObject jsonObject = JSON.parseObject(message);
		return jsonObject.get("code") != null;
	}
}
