package com.jkk.aihome.entity.DO;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity(name = "on_off_state")
public class OnOffStateDO implements Serializable {
	private static final long serialVersionUID = -6790703386576429956L;

	@Id
	private String stateId;

	private String textActive;

	private String textUnActive;

	private Boolean state;

	private String icon;

	private String iconActiveColor;

	private String iconUnActiveColor;
}
