package com.jkk.aihome.entity.VO.state;

import com.jkk.aihome.enums.StateType;
import lombok.Data;

@Data
public abstract class StateDetailVO {
	private String stateId;

	private String name;

	private StateType type;

	private Boolean canControl;
}
