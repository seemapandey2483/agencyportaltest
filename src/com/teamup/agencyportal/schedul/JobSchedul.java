package com.teamup.agencyportal.schedul;

import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.domain.ServiceClient;
import com.teamup.agencyportal.model.JobsDetail;
import com.teamup.agencyportal.service.AgentConfigInfoService;
import com.teamup.agencyportal.service.AgentPaymentConfigService;
import com.teamup.agencyportal.service.ConfigDownloadService;
import com.teamup.agencyportal.service.DepositService;
import com.teamup.agencyportal.service.JobActivityService;
import com.teamup.agencyportal.service.MonthlyBillService;

public class JobSchedul {
	private static Logger log = Logger.getLogger(JobSchedul.class);

	  	@SuppressWarnings("rawtypes")
		public static void configJobSchediler(JobsDetail jobsDetails,CommonDAO dao,AgentConfigInfoService agentConfigInfoService
				 ,AgentPaymentConfigService agentPaymentConfigService,ConfigDownloadService configDownloadService,DepositService depositService,
				 JobActivityService jobActivityService,MonthlyBillService monthlyBillService,ServiceClient serviceClient)  {
			
			try {
					
					 Class jobClass = Class.forName("com.teamup.agencyportal.schedul."+jobsDetails.getJobClassName());
			         Scheduler scheduler = new StdSchedulerFactory().getScheduler();
				     @SuppressWarnings("unchecked")
					 JobDetail  job = JobBuilder.newJob(jobClass).withIdentity(jobsDetails.getJobName(), jobsDetails.getJobGrpName()).build();
				     
				     job.getJobDataMap().put("dao", dao);
				     
				     job.getJobDataMap().put("agentConfigInfoService", agentConfigInfoService);
				     job.getJobDataMap().put("agentPaymentConfigService", agentPaymentConfigService);
				     job.getJobDataMap().put("configDownloadService", configDownloadService);
				     job.getJobDataMap().put("depositService", depositService);
				     job.getJobDataMap().put("jobActivityService", jobActivityService);
				     job.getJobDataMap().put("monthlyBillService", monthlyBillService);
				     job.getJobDataMap().put("serviceClient", serviceClient);
				     
				     Trigger trigger = null;
				     
				     if (jobsDetails.getStartFlag().equals("true")) {
				    		trigger = TriggerBuilder
				    		.newTrigger()
				    		.withIdentity(jobsDetails.getJobTrigName(),jobsDetails.getJobGrpName())
				    		.startNow()
				    		.build();
					   }else{
				    	     trigger = TriggerBuilder.newTrigger()
							 .withIdentity(jobsDetails.getJobTrigName(),jobsDetails.getJobGrpName())
							 .startAt(new Date(System.currentTimeMillis()))
							 .withSchedule(CronScheduleBuilder.cronSchedule(jobsDetails.getJobTrigStr()))
							 .build();  
				     }
				     
				     job.getJobDataMap().put("id", jobsDetails.getJobId());
				     String jobDataMap =  (String) job.getJobDataMap().get("1");
				     if (jobDataMap==null || !jobDataMap.equals("1") ) {
				    	 scheduler.deleteJob(job.getKey());
				     	 scheduler.unscheduleJob(trigger.getKey());
				     	 scheduler.start();
				     	 scheduler.scheduleJob(job, trigger);
				     }
	   		}catch(Exception ee){
				log.error(ee);
			}
		}
	  	
	  	public static void deleteJob(JobsDetail jobsDetails) {
	  		try{
	  			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
	  			scheduler.unscheduleJob(TriggerKey.triggerKey(jobsDetails.getJobTrigName(), jobsDetails.getJobGrpName()));
	  			scheduler.deleteJob(JobKey.jobKey(jobsDetails.getJobName(), jobsDetails.getJobGrpName()));
	  		  }catch(Exception e){log.error(e);}
	  }
	  
}