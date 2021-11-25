package com.jkk.aihome.strategy;
import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.ValueConfig;

import com.jkk.aihome.entity.request.state.OnOffAddStateRequest;
import com.jkk.aihome.entity.request.state.ValueAddStateRequest;
import com.jkk.aihome.enums.StateType;
import com.jkk.aihome.strategy.state.StateStrategy;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ValueStrategyTest {
	@Resource
	private StateStrategy valueStrategy;

	@org.junit.jupiter.api.Test
	void isMatch() {
	}

	@org.junit.jupiter.api.Test
	void getDetailByStateId() {
		System.out.println(JSON.toJSONString(valueStrategy.getDetailByStateId("add-2")));
	}

	@org.junit.jupiter.api.Test
	void addState() {
		ValueAddStateRequest valueAddStateRequest = new ValueAddStateRequest();
		valueAddStateRequest.setText("add-text");
		valueAddStateRequest.setIcon("add-icon");
		valueAddStateRequest.setState(50);
		valueAddStateRequest.setIconColorForMax("#max");
		ValueConfig valueConfig = new ValueConfig();
		valueConfig.setMin(0);
		valueConfig.setMax(100);
		valueConfig.setStep(1);
		valueAddStateRequest.setConfig(valueConfig);
		valueAddStateRequest.setDevId("add");
		valueAddStateRequest.setStateType(StateType.VALUE);
		valueAddStateRequest.setCanControl(false);
		valueAddStateRequest.setName("程序新添加的数值");


		valueStrategy.addState(valueAddStateRequest);
	}
}
