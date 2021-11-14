package com.jkk.aihome.entity.VO;

import com.jkk.aihome.enums.StateType;
import lombok.Data;

import java.util.Date;

@Data
public class StateVO {
	private String stateId;

	private String icon;

	private String name;

	private StateType type;

	private String state;

	private Date reportTime;

}
