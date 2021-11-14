package com.jkk.aihome.repository;

import com.jkk.aihome.entity.DO.OnOffStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnOffStateRepository extends JpaRepository<OnOffStateDO, Integer> {
	OnOffStateDO findByStateId(String stateId);
}
