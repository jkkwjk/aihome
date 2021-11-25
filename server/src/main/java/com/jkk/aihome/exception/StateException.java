package com.jkk.aihome.exception;

import com.jkk.aihome.enums.StateExceptionEnum;

public class StateException extends RuntimeException {
	public StateException(StateExceptionEnum esBusinessErrorEnum) {
		super(esBusinessErrorEnum.getMsg());
	}

	public StateException(StateExceptionEnum esBusinessErrorEnum, Throwable cause) {
		super(esBusinessErrorEnum.getMsg(), cause);
	}

	public StateException(StateExceptionEnum esBusinessErrorEnum, String message) {
		super(esBusinessErrorEnum.getMsg() + " " + message);
	}

	public StateException(String message) {
		super(message);
	}
}
