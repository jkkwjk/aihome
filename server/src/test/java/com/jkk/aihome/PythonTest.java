package com.jkk.aihome;

import com.jkk.aihome.util.PythonUtil;
import org.junit.jupiter.api.Test;
import org.python.core.*;
import org.python.util.PythonInterpreter;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class PythonTest {

	@Test
	public void test() throws IOException {
		String codePy = "def doAutoTask(states, globals):\n" +
				"\t# write your code here.\n" +
				"\tif globals['led_open']:\n" +
				"\t\tprint(\"open\")\n" +
				"\telse:\n" +
				"\t\tprint(\"close\")\n" +
				"\t\n" +
				"\t\n" +
				"\tif (states['test'] > 50):\n" +
				"\t\tglobals['led_open'] = False\n" +
				"\telse:\n" +
				"\t\tglobals['led_open'] = True\n" +
				"\t\n" +
				"\tpass";



		PyDictionary glob = new PyDictionary();
		glob.put("led_open", true);

		PyDictionary states = new PyDictionary();

		states.put("test", 100);
		System.out.println(PythonUtil.exec(codePy, states, glob));

		states.put("test", 1);
		System.out.println(PythonUtil.exec(codePy, states, glob));

		states.put("test", 100);
		System.out.println(PythonUtil.exec(codePy, states, glob));


		System.out.println(PythonUtil.exec("def doAutoTask(states, globals):\n" +
				"\t# write your code here.\n" +
				"\tprint(globals['led_open'])\n" +
				"\tpass", states, glob));
	}

	@Test
	public void testAsync() throws InterruptedException {
		PythonInterpreter pythonInterpreter = new PythonInterpreter();
		pythonInterpreter.exec("a = 1");
		Object lock = new Object();
		for (int i = 0; i < 100; ++i) {
			new Thread(() -> {
				synchronized (lock) {
					pythonInterpreter.exec("a += 1");
					pythonInterpreter.exec("print(a)");
				}
			}).start();
		}

		Thread.sleep(2000);
	}

	@Test
	public void testDict() throws InterruptedException {
		try {
			new PyDictionary();
		}catch (Exception e) {
			e.printStackTrace();
		}
//		PythonInterpreter pythonInterpreter = new PythonInterpreter();
//		pythonInterpreter.exec("a = 1");
//		Object lock = new Object();
//		for (int i = 0; i < 100; ++i) {
//			new Thread(() -> {
//				synchronized (lock) {
//					pythonInterpreter.exec("a += 1");
//					pythonInterpreter.exec("print(a)");
//				}
//			}).start();
//		}
//
//		Thread.sleep(2000);
	}
}
