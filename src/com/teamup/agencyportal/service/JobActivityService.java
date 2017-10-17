package com.teamup.agencyportal.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.model.JobActivity;
@Component
@Repository
public class JobActivityService {

	 Logger log = Logger.getLogger(getClass());

	public JobActivity getJobActivity(CommonDAO cm, String jobid) {

		String query = "select p from JobActivity p where JOBID='" + jobid	+ "'";
		return (JobActivity) cm.getObjectByQuery(query,JobActivity.class);
	}

	public long getMaxJobActivity(CommonDAO cm, String jobid) {

		String query = "select max(id) as ID from JobActivity where JOBID='"+ jobid + "'";
		Integer id = (Integer) cm.getObjectByQuery(query,Integer.class);
		log.info("getMaxJobActivity="+id);
		return id;
	}

	@SuppressWarnings("unchecked")
	public List<JobActivity> getJobActivityList(CommonDAO cm,String jobid, int startPageIndex, int recordsPerPage) {
		String query = "SELECT s FROM JobActivity s WHERE  jobId='" + jobid + "'";
		return (List<JobActivity>) (List<?>) cm.getAllObjectsForPaging(query,JobActivity.class,startPageIndex,recordsPerPage);
	}

	public long getJobActivityCount(CommonDAO cm, String jobid) {
		String query = "SELECT COUNT(ID) AS COUNT FROM JobActivity where JOBID='" + jobid + "'";
		return (Long) cm.getObjectByQuery(query,Long.class);
	}
}