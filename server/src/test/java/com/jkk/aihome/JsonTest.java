package com.jkk.aihome;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.hardware.request.ModeAddStateRequest;
import com.jkk.aihome.hardware.request.StateReportRequest;
import org.junit.jupiter.api.Test;

public class JsonTest {
	@Test
	public void test() {
		String json = "{\"devId\":\"bMo6rLU\",\"states\":[{\"id\":0,\"state\":false}]}";
		StateReportRequest stateReportRequest = JSON.parseObject(json, StateReportRequest.class);

		System.out.println(stateReportRequest);
	}

	@Test
	public void test2() {
		String json = "{\"red\":{\"color\":\"#BF0928\",\"icon\":\"el-icon-switch-button\",\"text\":\"亮红灯\",\"modeText\":\"红色\"},\"green\":{\"color\":\"#3A955D\",\"icon\":\"el-icon-switch-button\",\"text\":\"亮绿灯\",\"modeText\":\"绿色\"},\"stateType\":\"MODE\",\"name\":\"颜色选择\",\"yellow\":{\"color\":\"#F4D200\",\"icon\":\"el-icon-switch-button\",\"text\":\"亮黄灯\",\"modeText\":\"黄色\"},\"state\":\"red\",\"canControl\":true}";
		ModeAddStateRequest modeAddStateRequest = JSON.parseObject(json, ModeAddStateRequest.class);

		System.out.println(modeAddStateRequest);
	}
}
