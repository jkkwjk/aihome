
package com.jkk.aihome.util;

import com.jkk.aihome.datainject.IDataHolder;
import com.jkk.aihome.service.IAutoService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


/**
 * 定时任务工具
 *
 */
@Slf4j
@Component
public class ScheduleJobUtil extends QuartzJobBean {
	private final IAutoService autoService;

	public ScheduleJobUtil(IAutoService autoService) {
		this.autoService = autoService;
	}

	@Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String code = (String) context.getMergedJobDataMap().get("code");
        Object persistence = context.getMergedJobDataMap().get("persistence");
		if (code != null) {
			try {
				log.info("定时自动化任务输出结果: {}", autoService.runCode(code));
			} catch (Exception e) {
				log.error("定时自动化任务执行失败", e);
			}
		}else if (persistence instanceof IDataHolder) {
			// 数据内存持久化
			((IDataHolder<?, ?>) persistence).persistence();
		}


    }
}
