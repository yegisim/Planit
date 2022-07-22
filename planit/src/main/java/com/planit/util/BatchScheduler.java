package com.planit.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.planit.configuration.BatchConfiguration;

@Component
public class BatchScheduler {
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private BatchConfiguration batchConfiguration;
	
//	@Scheduled(cron = "0 0 0/6 * * * ")
	@Scheduled(cron = "0 0/10 * * * *")
//	@Scheduled(cron = "10 * * * * *")
	public void runJob() {
		System.out.println("현재 시간은" + new Date());
		
		Map<String, JobParameter> confMap = new HashMap<>();
		confMap.put("time", new JobParameter(System.currentTimeMillis()));
		JobParameters jobParameters = new JobParameters(confMap);
		
		try {
			jobLauncher.run(batchConfiguration.processJob(), jobParameters);
		} catch(JobExecutionAlreadyRunningException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException | org.springframework.batch.core.repository.JobRestartException e) {
			
			System.out.println(e.getMessage());
		}
	}
}
