package com.jkk.aihome.entity.VO.state;

import com.jkk.aihome.entity.ValueConfig;
import lombok.Data;

@Data
public class ValueStateDetailVO extends StateDetailVO {
	private String text;

	private String icon;

	private String iconColorForMax;

	private ValueConfig config;

	private Integer state;
}
