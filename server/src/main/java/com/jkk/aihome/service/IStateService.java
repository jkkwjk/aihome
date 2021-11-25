package com.jkk.aihome.service;

import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.entity.VO.StateVO;
import com.jkk.aihome.enums.StateType;

import java.util.List;

public interface IStateService {
	List<StateVO> findStateVOByDevId(String devId);

	List<StateDetailVO> findStateDetailVOByDevId(String devId);

	StateDetailVO findStateDetailVOByStateIdAndType(String stateId, StateType stateType);

	StateDetailVO findStateDetailVOByStateId(String stateId);

	Boolean updateStateNameByStateId(String stateId, String name);

	void deleteAllStateByDevId(String devId);
}
