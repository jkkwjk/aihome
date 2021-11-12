package com.jkk.aihome.repository.modestate;

import com.jkk.aihome.entity.DO.modestate.ModeStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeStateRepository extends JpaRepository<ModeStateDO, Integer> {
}
