package com.jkk.aihome.controller;

import com.jkk.aihome.entity.VO.R;
import com.jkk.aihome.service.IEventService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/event")
public class EventController {
	private final IEventService eventService;

	public EventController(IEventService eventService) {
		this.eventService = eventService;
	}

	@GetMapping
	public R<List<String>> findAllEvents() {
		return R.ok(eventService.findAllEvents());
	}
}
