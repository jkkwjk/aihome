package com.jkk.aihome.hardware.exception;

public class AppNameRepeatException extends RuntimeException{
	private String repeatAppName;

	public AppNameRepeatException(String repeatAppName) {
		super(repeatAppName + " repeat");
	}
}
