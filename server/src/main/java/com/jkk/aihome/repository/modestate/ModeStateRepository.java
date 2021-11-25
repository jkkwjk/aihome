package com.jkk.aihome.repository.modestate;

import com.jkk.aihome.entity.DO.modestate.ModeStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface ModeStateRepository extends JpaRepository<ModeStateDO, Integer> {
	ModeStateDO findByStateId(String stateId);

	Integer removeAllByStateIdIn(Collection<String> stateId);
}
