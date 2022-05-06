package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.ValueStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ValueStateRepository extends JpaRepository<ValueStateDO, String> {
	Integer removeAllByStateIdIn(Collection<String> stateId);
}
