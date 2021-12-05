package com.jkk.aihome.hardware.request;

import com.jkk.aihome.entity.ModeOption;
import lombok.Data;

import java.util.Map;

@Data
public class ModeAddStateRequest extends AddStateRequest {
	private Map<String, ModeOption> options;

	private String state;
}
