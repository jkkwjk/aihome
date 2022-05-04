package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity(name = "auto")
public class AutoDO implements Serializable {
	private static final long serialVersionUID = -2263369616500623062L;

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
