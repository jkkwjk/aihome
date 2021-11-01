package com.jkk.aihome.controller;

import com.jkk.aihome.service.AppService;
import com.jkk.aihome.util.RestfulResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {
	private final AppService appService;

	public AppController(AppService appService) {
		this.appService = appService;
	}

	@GetMapping
	RestfulResult getAllApp() {
		byte[] a = new byte[]{2,34,23,23,44,55,23,66,4};
		return RestfulResult.success(appService.findAll());
	}

}
