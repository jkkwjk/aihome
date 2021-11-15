package com.jkk.aihome.service;

import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.entity.VO.StateVO;

import java.util.List;

public interface IStateService {
	List<StateVO> findStateVOByDevId(String devId);

	List<StateDetailVO> findStateDetailVOByDevId(String devId);

	Boolean updateStateNameByStateId(String stateId, String name);

	void deleteAllStateByDevId(String devId);
}
