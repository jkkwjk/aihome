package com.jkk.aihome.strategy.auto;

import com.jkk.aihome.entity.DO.AutoDO;
import com.jkk.aihome.entity.DTO.AutoDTO;
import com.jkk.aihome.entity.VO.auto.AutoBaseVO;
import com.jkk.aihome.enums.AutoType;

public interface AutoExecuteStrategy {
	Boolean isMatch(AutoType autoType);

	/**
	 * 启用某个自动化 (需要校验自动化是否合法)
	 * @param autoDTO
	 * @return
	 */
	Boolean regedit(AutoDTO autoDTO);

	Boolean unRegedit(Integer id);

	AutoBaseVO buildAutoVOFromAutoDO(AutoDO autoDO);

	AutoDO addNew();
}
