package com.jkk.aihome.hardware.model;

public class HandleResult {
	private boolean isContinue;

	private String newData;

	public boolean isContinue() {
		return isContinue;
	}

	public void setContinue(boolean aContinue) {
		isContinue = aContinue;
	}

	public String getNewData() {
		return newData;
	}

	public void setNewData(String newData) {
		this.newData = newData;
	}

	public HandleResult(boolean isContinue, String newData) {
		this.isContinue = isContinue;
		this.newData = newData;
	}
}
