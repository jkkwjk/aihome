package com.jkk.aihome.service;

import com.jkk.aihome.entity.App;
import com.jkk.aihome.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AppService {
	List<App> findAll();
}
