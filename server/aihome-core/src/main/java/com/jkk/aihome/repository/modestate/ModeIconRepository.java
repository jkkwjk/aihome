package com.jkk.aihome.repository.modestate;

import com.jkk.aihome.entity.DO.modestate.ModeIconDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeIconRepository extends JpaRepository<ModeIconDO, Integer> {
}
