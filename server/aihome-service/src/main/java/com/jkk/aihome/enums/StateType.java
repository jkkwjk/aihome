package com.jkk.aihome.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum StateType {
	ON_OFF(0, "布尔型"),
	MODE(1, "枚举型"),
	VALUE(2, "数值型"),

	;


	private Integer type;

	private String description;

	public static StateType of(Integer type) {
		return Arrays.stream(StateType.values())
				.filter(stateType -> stateType.getType().equals(type))
				.findFirst().orElse(null);
	}
}
