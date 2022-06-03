package com.jkk.aihome.datainject.state;

import com.jkk.aihome.datainject.IDataHolder;
import com.jkk.aihome.entity.DO.modestate.ModeOptionDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

public interface ModeOptionRepository extends IDataHolder<ModeOptionDO, Integer> {
	List<ModeOptionDO> findAllByStateId(String stateId);

	ModeOptionDO findByStateIdAndModeValue(String stateId, String modeValue);

	Integer removeAllByStateIdIn(Collection<String> stateId);
}
