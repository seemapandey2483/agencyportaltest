package com.teamup.agencyportal.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.model.JobActivityTransationDetail;
@Component
@Repository
public class JobActivityTransactionService {
	 Logger log = Logger.getLogger(getClass());
	
	public JobActivityTransationDetail getJobActivityTransationDetail(CommonDAO manager,String jocid,int agentCID) {
	
	   String query = "select p from JobActivityTransationDetail p where AgentCId='"+agentCID+"' and  JactId='"+jocid+"'";
	   return (JobActivityTransationDetail)manager.getObjectByQuery(query,JobActivityTransationDetail.class);
	}

	@SuppressWarnings("unchecked")
	public List<JobActivityTransationDetail> getListTransationDetail(CommonDAO manager,String colsname,String value) {
	
		 String query = "select p from JobActivityTransationDetail p where "+colsname+"='"+value+"'";
		 return  (List<JobActivityTransationDetail>)(List<?>) manager.getAllObjectsByQuery(query,JobActivityTransationDetail.class);
	}

	@SuppressWarnings("unchecked")
	public List<JobActivityTransationDetail> getJobActivityTransactionList(CommonDAO manager,int jacid,int startPageIndex,int recordsPerPage)  {
		String query = "SELECT s FROM JobActivityTransationDetail s WHERE  jactId='" + jacid + "'";
		return (List<JobActivityTransationDetail>) (List<?>) manager.getAllObjectsForPaging(query,JobActivityTransationDetail.class,startPageIndex,recordsPerPage);
	}

	public long getJobActivityTransDetailsCount(CommonDAO manager,int jacid)
	{
			String query = "SELECT COUNT(id) AS COUNT FROM JobActivityTransationDetail where JACTID='"+jacid+"'" ;
			return (Long) manager.getObjectByQuery(query,Long.class);
   }

}