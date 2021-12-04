package com.jkk.aihome.service;

import com.jkk.aihome.entity.VO.state.StateDetailVO;

import java.util.List;

public interface IOverviewService {
	/**
	 * 有序的返回已经添加到概览的状态
	 * @return
	 */
	List<StateDetailVO> getAddedOverview();

	List<StateDetailVO> getUnAddOverview();

	StateDetailVO addLastByStateId(String stateId);

	void deleteOverviewByStateId(String stateId);

	void deleteAllOverviewByDevId(String devId);

	/**
	 * stateId插入到toStateId的前面
	 * @param stateId
	 * @param toStateId null表示插入到最后
	 * @return
	 */
	Boolean reorderOverview(String stateId, String toStateId);
}
