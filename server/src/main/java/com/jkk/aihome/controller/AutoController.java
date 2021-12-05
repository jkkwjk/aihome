package com.jkk.aihome.controller;

import com.jkk.aihome.entity.VO.auto.AutoBaseVO;
import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.enums.AutoType;
import com.jkk.aihome.service.IAutoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auto")
public class AutoController {
	private final IAutoService autoService;

	public AutoController(IAutoService autoService) {
		this.autoService = autoService;
	}

	@GetMapping("/timer")
	public R<List<AutoBaseVO>> getAllTimerAuto() {
		return R.ok(autoService.findAllByAutoType(AutoType.TIMER));
	}

	@GetMapping("/event")
	public R<List<AutoBaseVO>> getAllEventAuto() {
		return R.ok(autoService.findAllByAutoType(AutoType.EVENT));
	}

	@GetMapping("/code/{autoId}")
	public R<String> getCodeByAutoId(@PathVariable Integer autoId) {
		return R.ok(autoService.getCodeByAutoId(autoId));
	}

	@PostMapping
	public R<AutoBaseVO> addNewAuto(Integer type) {
		return R.ok(autoService.addAutoByAutoType(AutoType.of(type)));
	}

	@PostMapping("/tryOnce")
	public R<String> tryOnce(String code) {
		return R.ok(autoService.runCode(code));
	}

	@DeleteMapping("/{autoId}")
	public R<Boolean> deleteByAutoId(@PathVariable Integer autoId) {
		return R.ok(autoService.deleteByAutoId(autoId));
	}

	@PutMapping("/enable/{autoId}")
	public R<Boolean> modifyEnableByAutoId(@PathVariable Integer autoId, Boolean enable) {
		if (autoService.modifyEnableByAutoId(autoId, enable)) {
			return R.ok(true);
		}else {
			return R.error("不能生效该自动化");
		}
	}

	@PutMapping("/name/{autoId}")
	public R<Boolean> modifyNameByAutoId(@PathVariable Integer autoId, String name) {
		return R.ok(autoService.modifyNameByAutoId(autoId, name));
	}

	@PutMapping("/cron/{autoId}")
	public R<Boolean> modifyCronByAutoId(@PathVariable Integer autoId, String cron) {
		if (autoService.modifyCronByAutoId(autoId, cron)) {
			return R.ok(true);
		}else {
			return R.error("cron 表达式有误");
		}
	}

	@PutMapping("/code/{autoId}")
	public R<Boolean> modifyCodeByAutoId(@PathVariable Integer autoId, String code) {
		if (autoService.modifyCodeByAutoId(autoId, code)) {
			return R.ok(true);
		}else {
			return R.error("保存代码失败");
		}
	}
}
