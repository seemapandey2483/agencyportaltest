package com.teamup.agencyportal.schedul;

import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

import com.teamup.agencyportal.constant.AgencyPortalConstant;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.domain.ProcessPayment;
import com.teamup.agencyportal.service.AgentConfigInfoService;
import com.teamup.agencyportal.service.AgentPaymentConfigService;
import com.teamup.agencyportal.service.ConfigDownloadService;
import com.teamup.agencyportal.service.JobActivityService;
import com.teamup.agencyportal.service.MonthlyBillService;

public class PaymentJob implements Job {
	private Logger log = Logger.getLogger(PaymentJob.class);
	
	public void execute(JobExecutionContext context) {
		try {
			CommonDAO dao  = (CommonDAO) context.getJobDetail().getJobDataMap().get("dao");
			AgentConfigInfoService agentConfigInfoService  = (AgentConfigInfoService) context.getJobDetail().getJobDataMap().get("agentConfigInfoService");
			AgentPaymentConfigService agentPaymentConfigService  = (AgentPaymentConfigService) context.getJobDetail().getJobDataMap().get("agentPaymentConfigService");
			ConfigDownloadService configDownloadService  = (ConfigDownloadService) context.getJobDetail().getJobDataMap().get("configDownloadService");
			JobActivityService jobActivityService  = (JobActivityService) context.getJobDetail().getJobDataMap().get("jobActivityService");
			MonthlyBillService monthlyBillService  = (MonthlyBillService) context.getJobDetail().getJobDataMap().get("monthlyBillService");

			ProcessPayment processPayment = new ProcessPayment();
			processPayment.saveDefaultJobActivity(dao,(String) context.getJobDetail().getJobDataMap().get("id"));
			String jobId  = (String) context.getJobDetail().getJobDataMap().get("id");
			context.getJobDetail().getJobDataMap().put("1", "1");
			int jobID = (int)jobActivityService.getMaxJobActivity(dao,jobId);
			
			String []transactionCounts = processPayment.processPaymentJob(jobID,dao,agentConfigInfoService,agentPaymentConfigService,configDownloadService,monthlyBillService).split(",");
			 
			processPayment.updateDefaultJobActivity(dao,jobID,jobId,(Integer.parseInt(transactionCounts[1])),(Integer.parseInt(transactionCounts[0])),AgencyPortalConstant.STATUS_SUC);
			
			context.getJobDetail().getJobDataMap().put("1", "0");

		} catch (Exception e1) {
			log.error("PaymentJob" + e1);
		}
	}

}
