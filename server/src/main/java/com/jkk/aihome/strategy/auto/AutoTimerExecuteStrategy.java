package com.jkk.aihome.strategy.auto;

import com.jkk.aihome.entity.DO.AutoDO;
import com.jkk.aihome.entity.DTO.AutoDTO;
import com.jkk.aihome.entity.VO.auto.AutoBaseVO;
import com.jkk.aihome.entity.VO.auto.AutoTimerVO;
import com.jkk.aihome.enums.AutoType;
import com.jkk.aihome.repository.AutoRepository;
import com.jkk.aihome.util.ScheduleUtil;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class AutoTimerExecuteStrategy implements AutoExecuteStrategy{
	@Resource
	private Map<Integer, AutoDTO> autoTimer;

	private final Scheduler scheduler;
	private final AutoRepository autoRepository;

	public AutoTimerExecuteStrategy(Scheduler scheduler, AutoRepository autoRepository) {
		this.scheduler = scheduler;
		this.autoRepository = autoRepository;
	}

	@Override
	public Boolean isMatch(AutoType autoType) {
		return autoType.equals(AutoType.TIMER);
	}

	@Override
	public Boolean regedit(AutoDTO autoDTO) {
		if (autoDTO.getCron() == null || !CronExpression.isValidExpression(autoDTO.getCron())) {
			return false;
		}

		Integer autoId = autoDTO.getId();
		if (autoTimer.containsKey(autoId)) {
			// 删除老的定时任务
			ScheduleUtil.deleteScheduleJob(scheduler, autoId);
		}

		autoTimer.put(autoId, autoDTO);
		ScheduleUtil.createScheduleJob(scheduler, autoDTO);
		return true;
	}

	@Override
	public Boolean unRegedit(Integer id) {
		if (autoTimer.containsKey(id)) {
			autoTimer.remove(id);
			ScheduleUtil.deleteScheduleJob(scheduler, id);
		}
		return true;
	}

	@Override
	public AutoBaseVO buildAutoVOFromAutoDO(AutoDO autoDO) {
		AutoTimerVO autoTimerVO = new AutoTimerVO();
		BeanUtils.copyProperties(autoDO, autoTimerVO);

		return autoTimerVO;
	}

	@Override
	public AutoDO addNew() {
		AutoDO autoDO = new AutoDO();
		autoDO.setType(AutoType.TIMER.getType());
		autoDO.setName("未命名");
		autoDO.setEnable(false);
		autoDO.setCron(null);
		autoDO.setEvents(null);
		autoDO.setCode(null);

		autoDO = autoRepository.save(autoDO);
		return autoDO;
	}
}
