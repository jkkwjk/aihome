package com.jkk.aihome.controller;

import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.entity.VO.state.StateDetailVO;
import com.jkk.aihome.service.IOverviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/overview")
public class OverviewController {
	private final IOverviewService overviewService;

	public OverviewController(IOverviewService overviewService) {
		this.overviewService = overviewService;
	}

	@GetMapping
	public R<List<StateDetailVO>> getAddedOverview() {
		return R.ok(overviewService.getAddedOverview());
	}

	@GetMapping("/unadd")
	public R<List<StateDetailVO>> getUnAddOverview() {
		return R.ok(overviewService.getUnAddOverview());
	}

	@PostMapping
	public R<StateDetailVO> addOverview(String stateId) {
		return R.ok(overviewService.addLastByStateId(stateId));
	}

	@PutMapping
	public R<Boolean> reOrderOverview(String stateId, String toStateId) {
		return R.ok(overviewService.reorderOverview(stateId, toStateId));
	}

	@DeleteMapping("/{stateId}")
	public R<Boolean> deleteOverview(@PathVariable String stateId) {
		overviewService.deleteOverviewByStateId(stateId);
		return R.ok(true);
	}
}
