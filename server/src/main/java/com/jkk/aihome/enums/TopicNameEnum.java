package com.jkk.aihome.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TopicNameEnum {
	DISCOVER("discover/+", "设备发现"),
	REPORT("report/+", "状态上报"),
	CONTROL("control/+", "状态控制"),
	QUERY("query/+", "状态查询"),

	;

	private final String topic;
	private final String description;
}
