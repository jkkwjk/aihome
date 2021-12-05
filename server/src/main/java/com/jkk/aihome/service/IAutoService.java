package com.jkk.aihome.service;

import com.jkk.aihome.entity.VO.auto.AutoBaseVO;
import com.jkk.aihome.enums.AutoType;

import java.util.List;

public interface IAutoService {
	List<AutoBaseVO> findAllByAutoType(AutoType autoType);

	String getCodeByAutoId(Integer autoId);

	AutoBaseVO addAutoByAutoType(AutoType autoType);

	String runCode(String code);

	Boolean modifyEnableByAutoId(Integer autoId, Boolean enable);

	Boolean modifyNameByAutoId(Integer autoId, String name);

	Boolean modifyCronByAutoId(Integer autoId, String cron);

	Boolean modifyCodeByAutoId(Integer autoId, String code);

	Boolean deleteByAutoId(Integer autoId);
}
