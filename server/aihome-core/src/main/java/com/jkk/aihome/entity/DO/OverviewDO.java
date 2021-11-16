package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "overview")
public class OverviewDO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer beforeId;

	private String stateId;

	private Integer afterId;
}
