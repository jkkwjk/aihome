package com.jkk.aihome.entity.VO.auto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class AutoEventVO extends AutoBaseVO{
	private List<String> events;
}
