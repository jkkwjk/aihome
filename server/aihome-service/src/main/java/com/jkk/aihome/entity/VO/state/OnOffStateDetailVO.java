package com.jkk.aihome.entity.VO.state;

import lombok.Data;

@Data
public class OnOffStateDetailVO extends StateDetailVO {
	private String textActive;

	private String textUnActive;

	private String icon;

	private String iconActiveColor;

	private String iconUnActiveColor;

	private Boolean state;
}
