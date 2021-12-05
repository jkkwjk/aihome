package com.jkk.aihome.entity.VO.auto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AutoTimerVO extends AutoBaseVO{
	private String cron;
}
