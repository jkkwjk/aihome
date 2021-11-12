package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HardwareStateRepository extends JpaRepository<HardwareStateDO, Integer> {
	List<HardwareStateDO> findAllByDevId(String devId);
}
