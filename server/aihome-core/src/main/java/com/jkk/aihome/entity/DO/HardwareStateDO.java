package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 硬件和状态的映射表
 */
@Data
@Entity(name = "hardware_state")
public class HardwareStateDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String devId;

	private String stateId;

	private String type;

	private Boolean canControl;

	private String title;
}
