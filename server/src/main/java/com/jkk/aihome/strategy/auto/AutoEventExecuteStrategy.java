package com.jkk.aihome.strategy.auto;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.DO.AutoDO;
import com.jkk.aihome.entity.DTO.AutoDTO;
import com.jkk.aihome.entity.VO.auto.AutoBaseVO;
import com.jkk.aihome.entity.VO.auto.AutoEventVO;
import com.jkk.aihome.enums.AutoType;
import com.jkk.aihome.datainject.AutoRepository;
import org.python.netty.util.internal.ConcurrentSet;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;

@Component
public class AutoEventExecuteStrategy implements AutoExecuteStrategy{
	@Resource
	private Map<String, Set<AutoDTO>> autoEvent;

	private final AutoRepository autoRepository;

	public AutoEventExecuteStrategy(AutoRepository autoRepository) {
		this.autoRepository = autoRepository;
	}

	@Override
	public Boolean isMatch(AutoType autoType) {
		return AutoType.EVENT.equals(autoType);
	}

	@Override
	public Boolean regedit(AutoDTO autoDTO) {
		if (autoDTO.getEvents().size() == 0) {
			return false;
		}

		Boolean unRegeditResult = this.unRegedit(autoDTO.getId());
		if (unRegeditResult) {
			autoDTO.getEvents().forEach(eventId -> {
				Set<AutoDTO> responseSet = autoEvent.getOrDefault(eventId, new ConcurrentSet<>());
				responseSet.add(autoDTO);
				autoEvent.put(eventId, responseSet);
			});
		}

		return unRegeditResult;
	}

	@Override
	public Boolean unRegedit(Integer id) {
		autoEvent.forEach((eventId, autoDTOSet) -> {
			autoDTOSet.remove(new AutoDTO(id));
			if (autoDTOSet.size() == 0) {
				autoEvent.remove(eventId);
			}
		});
		return true;
	}

	@Override
	public AutoBaseVO buildAutoVOFromAutoDO(AutoDO autoDO) {
		AutoEventVO autoEventVO = new AutoEventVO();
		BeanUtils.copyProperties(autoDO, autoEventVO);

		autoEventVO.setEvents(JSON.parseArray(autoDO.getEvents(), String.class));
		return autoEventVO;
	}

	@Override
	public AutoDO addNew() {
		AutoDO autoDO = new AutoDO();
		autoDO.setType(AutoType.EVENT.getType());
		autoDO.setName("未命名");
		autoDO.setEnable(false);
		autoDO.setCron(null);
		autoDO.setEvents("[]");
		autoDO.setCode(null);

		autoDO = autoRepository.save(autoDO);
		return autoDO;
	}
}
