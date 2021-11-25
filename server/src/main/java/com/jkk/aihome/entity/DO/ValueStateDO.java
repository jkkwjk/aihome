package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "value_state")
public class ValueStateDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String stateId;

	private String text;

	private Integer state;

	private String icon;

	private String iconColorForMax;

	private Integer min;

	private Integer max;

	private Integer step;
}
