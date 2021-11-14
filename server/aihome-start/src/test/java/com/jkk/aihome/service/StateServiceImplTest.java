package com.jkk.aihome.service;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StateServiceImplTest {
	@Autowired
	private IStateService stateService;
	@Test
	void testFindStateDetailVOByDevId() {
		System.out.println(JSON.toJSONString(stateService.findStateDetailVOByDevId("add")));
	}
}
