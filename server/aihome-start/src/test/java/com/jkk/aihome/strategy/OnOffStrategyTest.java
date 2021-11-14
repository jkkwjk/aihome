package com.jkk.aihome.strategy;
import com.jkk.aihome.enums.StateType;

import com.jkk.aihome.entity.request.state.OnOffAddStateRequest;
import com.jkk.aihome.strategy.state.StateStrategy;
import org.junit.jupiter.api.AfterAll;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
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
		OnOffAddStateRequest onOffAddStateRequest = new OnOffAddStateRequest();
		onOffAddStateRequest.setTextActive("add-text");
		onOffAddStateRequest.setTextUnActive("add-text-un");
		onOffAddStateRequest.setIcon("add-icon");
		onOffAddStateRequest.setState(false);
		onOffAddStateRequest.setIconActiveColor("#add");
		onOffAddStateRequest.setIconUnActiveColor("#add-un");
		onOffAddStateRequest.setDevId("add");
		onOffAddStateRequest.setStateType(StateType.ON_OFF);
		onOffAddStateRequest.setCanControl(false);
		onOffAddStateRequest.setName("程序新添加的");


		onOffStrategy.addState(onOffAddStateRequest);
	}
}
