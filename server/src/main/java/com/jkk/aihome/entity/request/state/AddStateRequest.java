package com.jkk.aihome.entity.request.state;

import com.jkk.aihome.enums.StateType;
import lombok.Data;

@Data
public class AddStateRequest {
	private String devId;

	private StateType stateType;

	private Boolean canControl;

	private String name;
}
