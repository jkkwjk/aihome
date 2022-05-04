package com.jkk.aihome.util;

import com.jkk.aihome.datainject.IDataHolder;
import com.jkk.aihome.entity.DTO.AutoDTO;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;

import java.util.UUID;

/**
 * 定时任务工具类
 *
 */
@Slf4j
public class ScheduleUtil {
    private final static String JOB_NAME = "TIMER_AUTO_";

    /**
     * 获取触发器key
     */
    public static TriggerKey getTriggerKey(Integer jobId) {
        return TriggerKey.triggerKey(JOB_NAME + jobId);
    }

    /**
     * 获取jobKey
     */
    public static JobKey getAutoJobKey(Integer jobId) {
        return JobKey.jobKey(JOB_NAME + jobId);
    }

    /**
     * 获取表达式触发器
     */
    public static CronTrigger getCronTrigger(Scheduler scheduler, Integer jobId) {
        try {
            return (CronTrigger) scheduler.getTrigger(getTriggerKey(jobId));
        } catch (SchedulerException e) {
            log.error("获取定时任务CronTrigger出现异常", e);
            return null;
        }
    }

    /**
     * 创建定时任务
     */
    public static void createAutoScheduleJob(Scheduler scheduler, AutoDTO scheduleJob) {
        try {
        	//构建job信息
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJobUtil.class).withIdentity(getAutoJobKey(scheduleJob.getId())).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron())
            		.withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(scheduleJob.getId())).withSchedule(scheduleBuilder).build();

            jobDetail.getJobDataMap().put("code", scheduleJob.getCode());

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("创建定时任务失败", e);
        }
    }

    public static void createPersistenceScheduleJob(Scheduler scheduler, String cron, IDataHolder<?, ?> dataHolder) {
        try {
            //构建job信息
            String uuid = UUID.randomUUID().toString();
            JobDetail jobDetail = JobBuilder.newJob(ScheduleJobUtil.class).withIdentity(JobKey.jobKey(uuid)).build();

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cron)
                    .withMisfireHandlingInstructionDoNothing();

            //按新的cronExpression表达式构建一个新的trigger
            CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(TriggerKey.triggerKey(uuid)).withSchedule(scheduleBuilder).build();

            jobDetail.getJobDataMap().put("persistence", dataHolder);

            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            log.error("创建定时任务失败", e);
        }
    }

    /**
     * 更新定时任务
     */
    public static void updateAutoScheduleJob(Scheduler scheduler, AutoDTO scheduleJob) {
        try {
            TriggerKey triggerKey = getTriggerKey(scheduleJob.getId());

            //表达式调度构建器
            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getCron())
                    .withMisfireHandlingInstructionDoNothing();

            CronTrigger trigger = getCronTrigger(scheduler, scheduleJob.getId());

            //按新的cronExpression表达式重新构建trigger
            trigger = trigger.getTriggerBuilder().withIdentity(triggerKey).withSchedule(scheduleBuilder).build();

            scheduler.rescheduleJob(triggerKey, trigger);
        } catch (SchedulerException e) {
            log.error("更新定时任务失败", e);
        }
    }

    /**
     * 立即执行任务
     */
    public static void run(Scheduler scheduler, String id) {
        try {
            scheduler.triggerJob(JobKey.jobKey(id));
        } catch (SchedulerException e) {
            log.error("立即执行定时任务失败", e);
        }
    }

    /**
     * 删除定时任务
     */
    public static void deleteAutoScheduleJob(Scheduler scheduler, Integer jobId) {
        try {
            scheduler.deleteJob(getAutoJobKey(jobId));
        } catch (SchedulerException e) {
            log.error("删除定时任务失败", e);
        }
    }
}
