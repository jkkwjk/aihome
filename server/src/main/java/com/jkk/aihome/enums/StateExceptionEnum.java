package com.jkk.aihome.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StateExceptionEnum {
	NO_STATE_STRATEGY(1000, "没有对应的策略"),

	;
	private final Integer code;
	private final String msg;
}
