package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HardwareStateRepository extends JpaRepository<HardwareStateDO, String> {
	List<HardwareStateDO> findAllByDevIdOrderByReportTimeDesc(String devId);

	Optional<HardwareStateDO> findFirstByDevIdOrderByStateIdDesc(String devId);

	Integer removeAllByDevId(String devId);
}
