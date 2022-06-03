package com.jkk.aihome.datainject.state;

import com.jkk.aihome.entity.DO.OnOffStateDO;
import com.jkk.aihome.entity.DO.modestate.ModeStateDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

public interface ModeStateRepository extends IStateDataHolder<ModeStateDO, String> {
}
