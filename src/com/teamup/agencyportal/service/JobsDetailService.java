package com.teamup.agencyportal.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.teamup.agencyportal.constant.AgencyPortalConstant;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.domain.ServiceClient;
import com.teamup.agencyportal.model.JobsDetail;
import com.teamup.agencyportal.schedul.JobSchedul;
@Component
@Repository
public class JobsDetailService {

	 Logger log = Logger.getLogger(getClass());

	public JobsDetail getJobsDetails(CommonDAO dao, String jobid) {
		JobsDetail jDetail=  null;
		try{
		String query = "select p from JobsDetail p where JOBID='" + jobid + "'";
		log.info(query);
		 jDetail = (JobsDetail) dao.getObjectByQuery(query,JobsDetail.class);
		}catch(Exception e){return jDetail;}
		return jDetail;
	}

	public boolean deleteJobsDetails(CommonDAO dao, String jobid) {
		String query = "delete from JobsDetail where JOBID='" + jobid + "'";
		log.info(query);
		return (boolean) dao.getObjectByQuery(query,JobsDetail.class);
	}

	@SuppressWarnings("unchecked")
	public List<JobsDetail> getJobsDetailsList(CommonDAO dao) {
		return (List<JobsDetail>) (List<?>) dao.getAllObjectsByClassName("JobsDetail", JobsDetail.class);
	}

	public void addJobs(CommonDAO dao,JobsDetailService jobsDetailService) {
		JobsDetail jobsDetail = getJobsDetails(dao,AgencyPortalConstant.MONTHLYBILL_REPORT_JOB_ID);
		if (jobsDetail == null || jobsDetail.getId() == 0) {
			jobsDetail = new JobsDetail();
			jobsDetail.setJobId(AgencyPortalConstant.MONTHLYBILL_REPORT_JOB_ID);
			jobsDetail.setJobName(AgencyPortalConstant.DOWNLOAD_REPORT_JOB_NAME);
			jobsDetail.setJobGrpName(AgencyPortalConstant.MONTHLYBILL_REPORT_JOB_GROUP_NAME);
			jobsDetail.setJobDesc(AgencyPortalConstant.MONTHLYBILL_REPORT_JOB_DESC);
			jobsDetail.setJobTrigName(AgencyPortalConstant.MONTHLYBILL_REPORT_TRIG_NAME);
			jobsDetail.setJobTrigStr(AgencyPortalConstant.MONTHLYBILL_REPORT_TRIG_STR);
			jobsDetail.setJobClassName(AgencyPortalConstant.MONTHLYBILL_REPORT_CLASS_NAME);
			jobsDetail.setActive("Y");
			jobsDetail.setStartFlag("false");
			dao.addObject(jobsDetail);
		}
		jobsDetail = jobsDetailService.getJobsDetails(dao,AgencyPortalConstant.PAYMENT_REPORT_JOB_ID);
		if (jobsDetail == null || jobsDetail.getId() == 0) {
			jobsDetail = new JobsDetail();
			jobsDetail.setJobId(AgencyPortalConstant.PAYMENT_REPORT_JOB_ID);
			jobsDetail.setJobName(AgencyPortalConstant.PAYMENT_REPORT_JOB_NAME);
			jobsDetail.setJobGrpName(AgencyPortalConstant.PAYMENT_REPORT_JOB_GROUP_NAME);
			jobsDetail.setJobDesc(AgencyPortalConstant.PAYMENT_REPORT_JOB_DESC);
			jobsDetail.setJobTrigName(AgencyPortalConstant.PAYMENT_REPORT_TRIG_NAME);
			jobsDetail.setJobTrigStr(AgencyPortalConstant.PAYMENT_REPORT_TRIG_STR);
			jobsDetail.setJobClassName(AgencyPortalConstant.PAYMENT_REPORT_CLASS_NAME);
			jobsDetail.setActive("Y");
			jobsDetail.setStartFlag("false");
			dao.addObject(jobsDetail);
		}
	}
	
	public void saveDefaultJobs(CommonDAO dao,JobsDetailService jobsDetailService,AgentConfigInfoService agentConfigInfoService,AgentPaymentConfigService agentPaymentConfigService,ConfigDownloadService configDownloadService,DepositService depositService,JobActivityService jobActivityService,MonthlyBillService monthlyBillService,ServiceClient serviceClient) {
		try {
			List<JobsDetail> list = getJobsDetailsList(dao);
			if (list!=null&&list.size() == 2) {
					for (JobsDetail jd : list) {
						if (jd.getActive() != null && jd.getActive().equalsIgnoreCase("Y")) {
							jd.setStartFlag("false");
							JobSchedul.configJobSchediler(jd,dao,agentConfigInfoService,agentPaymentConfigService,configDownloadService,depositService,jobActivityService,monthlyBillService,serviceClient);
						}
					}
			} else {
					addJobs(dao,jobsDetailService);
					}
			} catch (Exception e) {
				log.error(e);
			}
	}	
}