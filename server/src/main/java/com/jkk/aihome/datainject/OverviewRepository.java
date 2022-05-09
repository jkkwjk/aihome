package com.jkk.aihome.datainject;

import com.jkk.aihome.entity.DO.OverviewDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

public interface OverviewRepository extends IDataHolder<OverviewDO, Integer> {
	OverviewDO findByStateId(String stateId);

	OverviewDO findByAfterIdNull();
}
