package com.jkk.aihome.hardware.request;

import lombok.Data;

import java.util.List;

@Data
public class DiscoverRequest {
	private String id;

	private String name;

	private String icon;

	private String devId;

	private String mac;

	// stateJson
	private List<String> states;
}
