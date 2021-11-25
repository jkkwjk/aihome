package com.jkk.aihome.hardware.request;

import lombok.Data;

@Data
public class GetDevIdRequest {
	private String id;

	private String mac;
}
