package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 硬件和状态的映射表
 */
@Data
@Entity(name = "hardware_state")
public class HardwareStateDO implements Serializable {

	private static final long serialVersionUID = -8783564397230654488L;

	@Id
	private String stateId;

	private String devId;

	private Integer type;

	private Boolean canControl;

	private String name;

	private String reportTime;
}
