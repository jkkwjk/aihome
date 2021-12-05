package com.jkk.aihome.hardware.request;

import com.jkk.aihome.entity.ValueConfig;
import lombok.Data;

@Data
public class ValueAddStateRequest extends AddStateRequest {
	private String text;

	private String icon;

	private Integer state;

	private String iconColorForMax;

	private ValueConfig config;
}
