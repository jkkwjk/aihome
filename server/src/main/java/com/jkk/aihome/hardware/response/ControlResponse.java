package com.jkk.aihome.hardware.response;

import com.jkk.aihome.hardware.HardwareState;
import lombok.Data;

@Data
public class ControlResponse {
	private String id;

	private String devId;

	private HardwareState states;
}
