package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "on_off_state")
public class OnOffStateDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String stateId;

	private String textActive;

	private String textUnActive;

	private Boolean state;

	private String icon;

	private String iconActiveColor;

	private String iconUnActiveColor;
}
