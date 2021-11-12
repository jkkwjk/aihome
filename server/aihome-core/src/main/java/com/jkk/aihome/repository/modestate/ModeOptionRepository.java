package com.jkk.aihome.repository.modestate;

import com.jkk.aihome.entity.DO.modestate.ModeOptionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeOptionRepository extends JpaRepository<ModeOptionDO, Integer> {
}
