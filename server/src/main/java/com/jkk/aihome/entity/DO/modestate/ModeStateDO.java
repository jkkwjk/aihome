package com.jkk.aihome.entity.DO.modestate;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity(name = "mode_state")
public class ModeStateDO {
	@Id
	private String stateId;

	private String state;
}
