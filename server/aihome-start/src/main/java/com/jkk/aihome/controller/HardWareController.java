package com.jkk.aihome.controller;

import com.jkk.aihome.entity.VO.HardwareWithStateVO;
import com.jkk.aihome.entity.request.AddHardWareRequest;
import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.service.IStateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hardware")
public class HardWareController {
	private final IHardwareService hardwareService;
	private final IStateService stateService;

	public HardWareController(IHardwareService hardwareService, IStateService stateService) {
		this.hardwareService = hardwareService;
		this.stateService = stateService;
	}

	@GetMapping
	public R<List<HardwareWithStateVO>> getHardware() {
		return R.ok(hardwareService.findAllHardwiredAndStates());
	}

	@DeleteMapping("/{devId}")
	public R<Boolean> deleteHardware(@PathVariable String devId) {
		hardwareService.deleteHardwareByDevId(devId);
		return R.ok(true);
	}

	@PostMapping
	public R<String> addHardware(AddHardWareRequest addHardWareRequest) {
		return null;
	}

	@PutMapping("/dev/{devId}")
	public R<Boolean> modifyDeviceName(@PathVariable String devId, String name) {
		return R.ok(hardwareService.updateHardwareNameByDevId(devId, name));
	}

	@PutMapping("/state/{stateId}")
	public R<Boolean> modifyStateName(@PathVariable String stateId, String name) {
		return R.ok(stateService.updateStateNameByStateId(stateId, name));
	}

}
