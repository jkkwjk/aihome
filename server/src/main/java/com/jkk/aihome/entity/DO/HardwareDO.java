package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity(name = "hardware")
public class HardwareDO implements Serializable {
	private static final long serialVersionUID = 2920240523520074603L;

	@Id
	private String devId;

	private String name;

	private String icon;

	private String mac;

	private Date discoverTime;

	private Date heartTime;
}
