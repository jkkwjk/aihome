package com.jkk.aihome.controller;

import com.jkk.aihome.util.RestfulResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class AppController {


	@GetMapping
	RestfulResult getAllApp() {
		return RestfulResult.success("1");
	}

}
