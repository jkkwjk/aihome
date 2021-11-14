package com.jkk.aihome.entity.VO.state;

import com.jkk.aihome.entity.VO.StateVO;
import lombok.Data;

@Data
public class OnOffStateDetailVO extends StateVO implements StateDetailVO {
	private String textActive;

	private String textUnActive;

	private Boolean canControl;

	private String iconActiveColor;

	private String iconUnActiveColor;
}
