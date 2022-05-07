package com.jkk.aihome.datainject;

import com.jkk.aihome.entity.DO.HardwareStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface HardwareStateRepository extends IDataHolder<HardwareStateDO, String> {
	List<HardwareStateDO> findAllByDevIdOrderByReportTimeDesc(String devId);

	Optional<HardwareStateDO> findFirstByDevIdOrderByStateIdDesc(String devId);

	Integer removeAllByDevId(String devId);
}
