package com.jkk.aihome.entity.VO.state;

import com.jkk.aihome.entity.ModeOption;
import com.jkk.aihome.entity.VO.StateVO;
import com.jkk.aihome.entity.ValueConfig;
import lombok.Data;

import java.util.Map;

@Data
public class ModeStateDetailVO extends StateVO implements StateDetailVO {
	private Boolean canControl;

	private Map<String, ModeOption> options;

	private String state;
}
