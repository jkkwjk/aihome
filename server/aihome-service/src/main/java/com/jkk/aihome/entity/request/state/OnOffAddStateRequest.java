package com.jkk.aihome.entity.request.state;

import lombok.Data;

@Data
public class OnOffAddStateRequest extends AddStateRequest {
	private String textActive;

	private String textUnActive;

	private String icon;

	private Boolean state;

	private String iconActiveColor;

	private String iconUnActiveColor;
}
