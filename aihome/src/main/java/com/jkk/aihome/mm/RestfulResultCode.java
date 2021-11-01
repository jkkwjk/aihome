package com.jkk.aihome.mm;

import lombok.Getter;

public enum RestfulResultCode {
	SUCCESS(200),
	FAIL(500);

	@Getter
	private Integer code;

	RestfulResultCode(Integer code) {
		this.code = code;
	}
}
