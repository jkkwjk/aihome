package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.ValueStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValueStateRepository extends JpaRepository<ValueStateDO, Integer> {
	ValueStateDO findByStateId(String stateId);
}
