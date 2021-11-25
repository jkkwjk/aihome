package com.jkk.aihome.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TopicNameEnum {
	DISCOVER("discover","discover/+", "设备发现"),
	DEV("dev","dev", "获取设备id"),
	REPORT("report","report/+", "状态上报"),
	CONTROL("control","control/+", "状态控制"),
	QUERY("query","query/+", "状态查询"),

	;

	private final String name;
	private final String topic;
	private final String description;
}
