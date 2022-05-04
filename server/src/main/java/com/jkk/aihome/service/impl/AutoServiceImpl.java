package com.jkk.aihome.service.impl;

import com.alibaba.fastjson.JSON;
import com.jkk.aihome.entity.DO.AutoDO;
import com.jkk.aihome.entity.DTO.AutoDTO;
import com.jkk.aihome.entity.VO.auto.AutoBaseVO;
import com.jkk.aihome.entity.VO.PythonRunResultVO;
import com.jkk.aihome.enums.AutoType;
import com.jkk.aihome.enums.TopicNameEnum;
import com.jkk.aihome.exception.IdNotFindException;
import com.jkk.aihome.hardware.request.StateReportRequest;
import com.jkk.aihome.datainject.AutoRepository;
import com.jkk.aihome.service.IAutoService;
import com.jkk.aihome.service.IHardwareService;
import com.jkk.aihome.service.IMqttService;
import com.jkk.aihome.strategy.auto.AutoExecuteStrategy;
import com.jkk.aihome.strategy.auto.AutoStrategyManagement;
import com.jkk.aihome.strategy.state.StateStrategyManagement;
import com.jkk.aihome.strategy.subscribe.SubscribeStrategyManagement;
import com.jkk.aihome.util.IdUtil;
import com.jkk.aihome.util.PythonUtil;
import lombok.extern.slf4j.Slf4j;
import org.python.core.PyDictionary;
import org.quartz.CronExpression;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AutoServiceImpl implements IAutoService {
	private final PyDictionary globals = new PyDictionary();
	private final PyDictionary states = new PyDictionary();

	@Resource
	private Map<String, Set<AutoDTO>> autoEvent;

	private final AutoRepository autoRepository;

	private final AutoStrategyManagement autoStrategyManagement;
	private final SubscribeStrategyManagement subscribeStrategyManagement;
	private final StateStrategyManagement stateStrategyManagement;

	private final IMqttService mqttService;
	private final IHardwareService hardwareService;

	public AutoServiceImpl(AutoRepository autoRepository, AutoStrategyManagement autoStrategyManagement, SubscribeStrategyManagement subscribeStrategyManagement, StateStrategyManagement stateStrategyManagement, IMqttService mqttService, IHardwareService hardwareService) {
		this.autoRepository = autoRepository;
		this.autoStrategyManagement = autoStrategyManagement;
		this.subscribeStrategyManagement = subscribeStrategyManagement;
		this.stateStrategyManagement = stateStrategyManagement;
		this.mqttService = mqttService;
		this.hardwareService = hardwareService;
	}

	@Override
	public List<AutoBaseVO> findAllByAutoType(AutoType autoType) {
		return autoRepository.findByTypeOrderByIdDesc(autoType.getType()).stream()
				.map(autoDO -> autoStrategyManagement.getAutoStrategyByAutoType(autoType).buildAutoVOFromAutoDO(autoDO))
				.collect(Collectors.toList());
	}

	@Override
	public String getCodeByAutoId(Integer autoId) {
		AutoDO autoDO = autoRepository.findById(autoId).orElseThrow(() -> new IdNotFindException(autoId, "auto"));
		return autoDO.getCode();
	}

	@Override
	public AutoBaseVO addAutoByAutoType(AutoType autoType) {
		AutoExecuteStrategy autoExecuteStrategy = autoStrategyManagement.getAutoStrategyByAutoType(autoType);
		return autoExecuteStrategy.buildAutoVOFromAutoDO(autoExecuteStrategy.addNew());
	}

	@Override
	public String runCode(String code) {
		PythonRunResultVO pythonRunResultVO = PythonUtil.exec(code, states, globals);

		pythonRunResultVO.getRetObj().forEach(mqttService::sendControlCMD);
		return pythonRunResultVO.getConsoleOut();
	}

	@Override
	public Boolean modifyEnableByAutoId(Integer autoId, Boolean enable) {
		// 真正的启用或者关闭自动化
		AutoDO autoDO = autoRepository.findById(autoId).orElseThrow(() -> new IdNotFindException(autoId, "auto"));
		AutoExecuteStrategy autoExecuteStrategy = autoStrategyManagement.getAutoStrategyByAutoId(autoId);
		Boolean modifyInMemory = false;
		if (enable) {
			if (StringUtils.hasText(autoDO.getCode())) {
				AutoDTO autoDTO = buildAutoDTOFromDO(autoDO);
				modifyInMemory = autoExecuteStrategy.regedit(autoDTO);
			}
		}else {
			modifyInMemory = autoExecuteStrategy.unRegedit(autoId);
		}

		// 如果设置成功, 把结果持久化
		if (modifyInMemory) {
			autoDO.setEnable(enable);
			autoDO = autoRepository.save(autoDO);
		}

		return modifyInMemory && autoDO.getEnable().equals(enable);
	}

	@Override
	public Boolean modifyNameByAutoId(Integer autoId, String name) {
		AutoDO autoDO = autoRepository.findById(autoId).orElseThrow(() -> new IdNotFindException(autoId, "auto"));
		autoDO.setName(name);
		autoDO = autoRepository.save(autoDO);
		return autoDO.getName().equals(name);
	}

	@Override
	public Boolean modifyCronByAutoId(Integer autoId, String cron) {
		if (!CronExpression.isValidExpression(cron)) {
			return false;
		}
		AutoDO autoDO = autoRepository.findById(autoId).orElseThrow(() -> new IdNotFindException(autoId, "auto"));
		autoDO.setCron(cron);

		return modifyAutoState(autoDO);
	}

	@Override
	public Boolean modifyEventByAutoId(Integer autoId, List<String> event) {
		AutoDO autoDO = autoRepository.findById(autoId).orElseThrow(() -> new IdNotFindException(autoId, "auto"));
		autoDO.setEvents(JSON.toJSONString(event));
		if (event.size() == 0) {
			autoDO.setEnable(false);
		}

		return modifyAutoState(autoDO);
	}

	@Override
	public Boolean modifyCodeByAutoId(Integer autoId, String code) {
		AutoDO autoDO = autoRepository.findById(autoId).orElseThrow(() -> new IdNotFindException(autoId, "auto"));
		autoDO.setCode(code);

		return modifyAutoState(autoDO);
	}

	private Boolean modifyAutoState(AutoDO autoDO) {
		autoDO = autoRepository.save(autoDO);

		// 修改自动化在内存中的状态
		AutoDTO autoDTO = buildAutoDTOFromDO(autoDO);
		AutoExecuteStrategy autoExecuteStrategy = autoStrategyManagement.getAutoStrategyByAutoType(AutoType.of(autoDO.getType()));
		if (autoDO.getEnable()) {
			return autoExecuteStrategy.regedit(autoDTO);
		}else {
			return autoExecuteStrategy.unRegedit(autoDTO.getId());
		}
	}

	@Override
	public Boolean deleteByAutoId(Integer autoId) {
		Boolean isModify = autoStrategyManagement.getAutoStrategyByAutoId(autoId).unRegedit(autoId);
		autoRepository.deleteById(autoId);
		return isModify;
	}


	@PostConstruct
	private void init() {
		Map<Integer, List<AutoDO>> autoTypeMap = autoRepository.findAllByEnableTrue().stream().collect(Collectors.groupingBy(AutoDO::getType));

		autoTypeMap.forEach((type, autoDOList) -> {
			AutoExecuteStrategy autoExecuteStrategy = autoStrategyManagement.getAutoStrategyByAutoType(AutoType.of(type));

			autoDOList.forEach(autoDO -> {
				AutoDTO autoDTO = buildAutoDTOFromDO(autoDO);
				autoExecuteStrategy.regedit(autoDTO);
			});
		});

		hardwareService.findAllHardwiredAndStates().forEach(hardwareWithStateVO -> hardwareWithStateVO.getStates().forEach(stateVO -> {
			String stateId = stateVO.getStateId();
			Object state = stateStrategyManagement.getStateStrategyByStateId(stateId).convertStateToObject(stateVO.getState());

			states.put(stateId, state);
		}));
		subscribeStrategyManagement.getSubscribeStrategyByName(TopicNameEnum.REPORT).addObserver((o, arg) -> {
			StateReportRequest stateReportRequest = (StateReportRequest) arg;
			stateReportRequest.getStates().forEach(hardwareState -> {
				String stateId = IdUtil.getStateIdFromDevIdAndId(stateReportRequest.getDevId(), hardwareState.getId());
				states.put(stateId, hardwareState.getState());

				// 触发事件驱动自动化
				Set<AutoDTO> autoDTOSet = autoEvent.getOrDefault(IdUtil.generateStateEventId(stateId), new HashSet<>());
				autoDTOSet.forEach(autoDTO -> log.info("触发事件状态ID: {}, 事件自动化任务输出结果: {}", stateId, this.runCode(autoDTO.getCode())));
			});
		});
	}

	private AutoDTO buildAutoDTOFromDO(AutoDO autoDO) {
		AutoDTO autoDTO = new AutoDTO();
		BeanUtils.copyProperties(autoDO, autoDTO);
		autoDTO.setEvents(JSON.parseArray(autoDO.getEvents(), String.class));

		return autoDTO;
	}
}
