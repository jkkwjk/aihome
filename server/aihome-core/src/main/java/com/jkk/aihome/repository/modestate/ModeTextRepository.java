package com.jkk.aihome.repository.modestate;

import com.jkk.aihome.entity.DO.modestate.ModeTextDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeTextRepository extends JpaRepository<ModeTextDO, Integer> {
}
