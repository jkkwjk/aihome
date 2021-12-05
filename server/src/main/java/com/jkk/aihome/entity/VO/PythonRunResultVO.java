package com.jkk.aihome.entity.VO;

import lombok.Data;
import org.python.core.PyObject;

import java.util.Map;

@Data
public class PythonRunResultVO {
	private String consoleOut;

	private Map<String, String> retObj;
}
