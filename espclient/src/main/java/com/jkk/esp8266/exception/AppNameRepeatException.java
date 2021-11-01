package com.jkk.esp8266.exception;

public class AppNameRepeatException extends RuntimeException{
	private String repeatAppName;

	public AppNameRepeatException(String repeatAppName) {
		super(repeatAppName + " repeat");
	}
}
