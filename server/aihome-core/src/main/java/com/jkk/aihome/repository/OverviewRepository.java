package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.OverviewDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OverviewRepository extends JpaRepository<OverviewDO, Integer> {
	OverviewDO findByStateId(String stateId);

	OverviewDO findByAfterIdNull();
}
