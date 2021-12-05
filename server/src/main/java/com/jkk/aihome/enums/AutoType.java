package com.jkk.aihome.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum AutoType {
	TIMER(0, "定时任务"),
	EVENT(1, "事件驱动任务"),

	;


	private final Integer type;

	private final String description;

	public static AutoType of(Integer type) {
		return Arrays.stream(AutoType.values())
				.filter(stateType -> stateType.getType().equals(type))
				.findFirst().orElse(null);
	}
}
