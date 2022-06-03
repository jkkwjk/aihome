package com.jkk.aihome.entity.DO.modestate;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity(name = "mode_state")
public class ModeStateDO implements Serializable {
	private static final long serialVersionUID = 237604312165611181L;

	@Id
	private String stateId;

	private String state;
}
