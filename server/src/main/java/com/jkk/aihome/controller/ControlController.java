package com.jkk.aihome.controller;

import com.jkk.aihome.entity.VO.R;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ControlController {
	@GetMapping("/{stateId}")
	public R<String> getState(@PathVariable String stateId) {
		return R.ok("1");
	}

	@PutMapping("/{stateId}")
	public R<String> modifyState(@PathVariable String stateId, String state) {
		return R.ok("2");
	}
}
