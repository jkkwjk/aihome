package com.jkk.aihome.service;

import org.junit.jupiter.api.Test;
import org.quartz.CronExpression;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ScheduleTest {
	@Autowired
	private Scheduler scheduler;

	@Test
	public void test() {
//		AutoTimerJob autoTimerJob = new AutoTimerJob();
//		autoTimerJob.setId(0);
//		autoTimerJob.setCron("");

		CronExpression.isValidExpression("* * * * * ? *");
//		ScheduleUtils.createScheduleJob(scheduler, );
	}
}
