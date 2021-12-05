package com.jkk.aihome.util;

import com.jkk.aihome.entity.VO.PythonRunResultVO;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

public class PythonUtil {
	private static final PythonInterpreter pythonInterpreter = new PythonInterpreter();

	public static synchronized PythonRunResultVO exec(String code, PyDictionary states, PyDictionary globals) {
		Map<String, String> retMap = new HashMap<>();

		Writer writer = new StringWriter();
		pythonInterpreter.setOut(writer);

		try {
			pythonInterpreter.exec(code);
			PyFunction pyFunction = pythonInterpreter.get("doAutoTask", PyFunction.class);
			PyObject ret = pyFunction.__call__(states, globals);
			if (ret instanceof PyDictionary) {
				PyDictionary retDict = (PyDictionary) ret;
				retDict.keySet().forEach((o) -> retMap.put(o.toString(), retDict.get(o).toString()));
			}
		}catch (PyException e) {
			// 代码执行异常
			PythonRunResultVO pythonRunResultVO = new PythonRunResultVO();
			pythonRunResultVO.setConsoleOut(e.toString());
			pythonRunResultVO.setRetObj(retMap);
			return pythonRunResultVO;
		}

		PythonRunResultVO pythonRunResultVO = new PythonRunResultVO();
		pythonRunResultVO.setConsoleOut(writer.toString());
		pythonRunResultVO.setRetObj(retMap);
		return pythonRunResultVO;
	}
}
