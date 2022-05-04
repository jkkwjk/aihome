package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "hardware")
public class HardwareDO {
	@Id
	private String devId;

	private String name;

	private String icon;

	private String mac;

	private Date discoverTime;

	private Date heartTime;
}
