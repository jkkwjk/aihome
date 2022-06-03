package com.jkk.aihome.entity.DO.modestate;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity(name = "mode_option")
public class ModeOptionDO implements Serializable {
	private static final long serialVersionUID = -2074077671700817614L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String stateId;

	private String modeValue;

	/**
	 * 友好的模式显示
	 */
	private String modeText;

	/**
	 * 主要显示文字
	 */
	private String text;

	private String icon;

	private String color;
}
