package com.jkk.aihome.exception;

public class IdNotFindException extends RuntimeException {
	public IdNotFindException(Integer id, String tableName) {
		super(id + " in " + tableName + " not find");
	}
}
