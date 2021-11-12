package com.jkk.aihome.controller;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.DO.HardwareDO;
import com.jkk.aihome.entity.request.AddHardWareRequest;
import com.jkk.aihome.entity.vo.R;
import com.jkk.aihome.service.IHardwareService;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/hardware")
public class HardWareController {
	private final IHardwareService hardwareService;

	public HardWareController(IHardwareService hardwareService) {
		this.hardwareService = hardwareService;
	}

	@GetMapping
	public R<String> getHardware() {
		return R.ok(JSON.toJSONString(hardwareService.finAll()));
	}

	@DeleteMapping
	public R<String> deleteHardware() {
		return R.ok("1");
	}

	@PostMapping
	public R<String> addHardware(AddHardWareRequest addHardWareRequest) {
		HardwareDO hardwareDO = new HardwareDO();
		hardwareDO.setDiscoverTime(new Date());
		hardwareDO.setHeartTime(new Date());
		hardwareDO.setDevId("test");
		hardwareDO.setIcon("icon");
		hardwareDO.setIp(addHardWareRequest.getIp());
		hardwareDO.setName(addHardWareRequest.getName());

		hardwareService.add(hardwareDO);
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
