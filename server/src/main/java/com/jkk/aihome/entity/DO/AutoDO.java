package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "auto")
public class AutoDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer type;

	private String name;

	private Boolean enable;

	private String cron;

	private String events;

	private String code;
}
