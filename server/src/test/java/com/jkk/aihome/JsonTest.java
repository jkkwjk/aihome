package com.jkk.aihome;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.hardware.request.StateReportRequest;
import org.junit.jupiter.api.Test;

public class JsonTest {
	@Test
	public void test() {
		String json = "{\"devId\":\"bMo6rLU\",\"states\":[{\"id\":0,\"state\":false}]}";
		StateReportRequest stateReportRequest = JSON.parseObject(json, StateReportRequest.class);

		System.out.println(stateReportRequest);
	}
}
