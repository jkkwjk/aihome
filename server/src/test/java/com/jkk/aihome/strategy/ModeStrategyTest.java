package com.jkk.aihome.strategy;
import com.jkk.aihome.entity.ModeOption;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.ValueConfig;
import com.jkk.aihome.entity.request.state.ModeAddStateRequest;
import com.jkk.aihome.entity.request.state.ValueAddStateRequest;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.strategy.state.StateStrategy;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ModeStrategyTest {
	@Resource
	private StateStrategy modeStrategy;

	@org.junit.jupiter.api.Test
	void isMatch() {
	}

	@org.junit.jupiter.api.Test
	void getDetailByStateId() {
		System.out.println(JSON.toJSONString(modeStrategy.getDetailByStateId("add-3")));
	}

	@org.junit.jupiter.api.Test
	void addState() {
		ModeAddStateRequest modeAddStateRequest = new ModeAddStateRequest();
		Map<String, ModeOption> modeOptionMap = new HashMap<>();
		ModeOption modeOption1 = new ModeOption();
		modeOption1.setModeText("节能");
		modeOption1.setIcon("ico-jieneng");
		modeOption1.setColor("#jienen");
		modeOption1.setText("节能模式");
		modeOptionMap.put("eco", modeOption1);

		ModeOption modeOption2 = new ModeOption();
		modeOption2.setModeText("自动");
		modeOption2.setIcon("ico-zidong");
		modeOption2.setColor("#zidong");
		modeOption2.setText("自动模式");
		modeOptionMap.put("auto", modeOption2);

		ModeOption modeOption3 = new ModeOption();
		modeOption3.setModeText("强力");
		modeOption3.setIcon("ico-qiangli");
		modeOption3.setColor("#power");
		modeOption3.setText("强力模式");
		modeOptionMap.put("power", modeOption3);


		modeAddStateRequest.setOptions(modeOptionMap);
		modeAddStateRequest.setState("eco");
		modeAddStateRequest.setDevId("add");
		modeAddStateRequest.setStateType(StateType.MODE);
		modeAddStateRequest.setCanControl(false);
		modeAddStateRequest.setName("程序新添加的模式");


		modeStrategy.addState(modeAddStateRequest);
	}
}
