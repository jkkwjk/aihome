package com.jkk.aihome.controller;

import com.jkk.aihome.entity.request.AddHardWareRequest;
import com.jkk.aihome.util.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("hardware")
public class HardWareController {
	@GetMapping
	public R<String> getHardware() {
		return R.ok("1");
	}

	@DeleteMapping
	public R<String> deleteHardware() {
		return R.ok("1");
	}

	@PostMapping
	public R<String> addHardware(AddHardWareRequest addHardWareRequest) {
		return R.ok("devid");
	}

	@PutMapping("/dev/{devId}")
	public R<String> modifyDeviceName(@PathVariable String devId, String name) {
		return R.ok("devid");
	}

	@PutMapping("/state/{stateId}")
	public R<String> modifyStateName(@PathVariable String stateId, String name) {
		return R.ok("devid");
	}

}
