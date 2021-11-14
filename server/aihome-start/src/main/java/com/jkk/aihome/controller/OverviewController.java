package com.jkk.aihome.controller;

import com.jkk.aihome.entity.VO.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/overview")
public class OverviewController {
	@GetMapping
	public R<String> getOverview() {
		return R.ok("2313");
	}

	@GetMapping("/unadd")
	public R<String> getUnAddOverview() {
		return R.ok("2313");
	}

	@PostMapping
	public R<String> addOverview() {
		return R.ok("2313");
	}

	@PutMapping
	public R<String> reOrderOverview(String stateId, Integer to) {
		return R.ok("2313");
	}

	@DeleteMapping("/{stateId}")
	public R<String> deleteOverview(@PathVariable String stateId) {
		return R.ok("2313");
	}
}
