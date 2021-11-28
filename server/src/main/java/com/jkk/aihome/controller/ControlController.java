package com.jkk.aihome.controller;

import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.service.IMqttService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/control")
public class ControlController {
	private final IMqttService mqttService;

	public ControlController(IMqttService mqttService) {
		this.mqttService = mqttService;
	}

	@GetMapping("/{stateId}")
	public R<String> getState(@PathVariable String stateId) {
		return R.ok("1");
	}

	@PutMapping("/{stateId}")
	public R<Boolean> modifyState(@PathVariable String stateId, String state) {
		mqttService.sendControlCMD(stateId, state);
		return R.ok(true);
	}
}
