package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.HardwareDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HardwareRepository extends JpaRepository<HardwareDO, Integer> {
	HardwareDO findByDevId(String devId);

	Integer removeByDevId(String devId);
}
