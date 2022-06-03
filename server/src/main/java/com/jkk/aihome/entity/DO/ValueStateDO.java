package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity(name = "value_state")
public class ValueStateDO implements Serializable {
	private static final long serialVersionUID = -3248989896666386666L;

	@Id
	private String stateId;

	private String text;

	private Integer state;

	private String icon;

	private String iconColorForMax;

	private Integer min;

	private Integer max;

	private Integer step;
}
