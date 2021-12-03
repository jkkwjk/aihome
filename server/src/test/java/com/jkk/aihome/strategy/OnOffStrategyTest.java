package com.jkk.aihome.strategy;
import com.jkk.aihome.enums.StateType;

import com.jkk.aihome.entity.request.state.OnOffAddStateRequest;
import com.jkk.aihome.strategy.state.StateStrategy;
import org.junit.jupiter.api.AfterAll;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OnOffStrategyTest {
	@Resource
	private StateStrategy onOffStrategy;

	@org.junit.jupiter.api.Test
	void isMatch() {
	}

	@org.junit.jupiter.api.Test
	void getDetailByStateId() {
		onOffStrategy.getDetailByStateId("test-1");
	}

	@org.junit.jupiter.api.Test
	void addState() {
		for (int i = 0; i < 10; i++) {
			OnOffAddStateRequest onOffAddStateRequest = new OnOffAddStateRequest();
			onOffAddStateRequest.setTextActive("开");
			onOffAddStateRequest.setTextUnActive("关");
			onOffAddStateRequest.setIcon("el-icon-switch-button");
			onOffAddStateRequest.setState(false);
			onOffAddStateRequest.setIconActiveColor("#000000");
			onOffAddStateRequest.setIconUnActiveColor("#aabbcc");
			onOffAddStateRequest.setDevId("test");
			onOffAddStateRequest.setStateType(StateType.ON_OFF);
			onOffAddStateRequest.setCanControl(true);
			onOffAddStateRequest.setName("程序新添加的");
			onOffStrategy.addState(onOffAddStateRequest);
		}
	}
}
