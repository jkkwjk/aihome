package com.jkk.aihome.hardware.request;

import com.jkk.aihome.enums.StateType;
import lombok.Data;

@Data
public class AddStateRequest {
	private String devId;

	private StateType stateType;

	private Boolean canControl;

	private String name;
}
