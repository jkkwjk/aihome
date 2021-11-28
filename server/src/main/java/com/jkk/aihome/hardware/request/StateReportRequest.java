package com.jkk.aihome.hardware.request;

import com.jkk.aihome.hardware.HardwareState;
import lombok.Data;

import java.util.List;

@Data
public class StateReportRequest {

	private String id;

	private String devId;

	private List<HardwareState> states;
}
