package com.jkk.aihome.entity.VO;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class HardwareWithStateVO {
	private String devId;

	private String icon;

	private String name;

	private String ip;

	private Date heartTime;

	private Date discoverTime;

	private List<StateVO> states;
}
