package com.jkk.aihome.repository.modestate;

import com.jkk.aihome.entity.DO.modestate.ModeOptionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface ModeOptionJpaRepository extends JpaRepository<ModeOptionDO, Integer> {
}
