package com.jkk.aihome.entity.VO.state;

import com.jkk.aihome.entity.ModeOption;
import lombok.Data;

import java.util.Map;

@Data
public class ModeStateDetailVO extends StateDetailVO {
	private Map<String, ModeOption> options;

	private String state;
}
