package com.jkk.aihome.service.impl;

import com.jkk.aihome.entity.App;
import com.jkk.aihome.repository.AppRepository;
import com.jkk.aihome.service.AppService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppServiceImpl implements AppService {
	private final AppRepository appRepository;

	public AppServiceImpl(AppRepository appRepository) {
		this.appRepository = appRepository;
	}

	@Override
	public List<App> findAll() {
		return appRepository.findAll();
	}
}
