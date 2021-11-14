package com.jkk.aihome.entity.VO.state;

import com.jkk.aihome.entity.VO.StateVO;
import com.jkk.aihome.entity.ValueConfig;
import lombok.Data;

@Data
public class ValueStateDetailVO extends StateVO implements StateDetailVO {
	private String text;

	private Boolean canControl;

	private String iconColorForMax;

	private ValueConfig config;
}
