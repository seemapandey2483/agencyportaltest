package com.teamup.agencyportal.service;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.domain.ServiceClient;
import com.teamup.agencyportal.model.AgentConfigInfo;

@Component
@Repository
public class AgentConfigInfoService {

	 Logger log = Logger.getLogger(getClass());
	public AgentConfigInfo getAgentConfigInfoById(CommonDAO dao,String agentId) {

		String query = "SELECT p FROM AgentConfigInfo p WHERE AGENTID='" + agentId + "'";
		return (AgentConfigInfo) dao.getObjectByQuery(query,AgentConfigInfo.class);
	}

	public AgentConfigInfo getAgentConfigInfoByAgentCid(CommonDAO dao, int agentCId) {
		String query = "SELECT p FROM AgentConfigInfo p WHERE AGENTCID='"+ agentCId + "'";
		return (AgentConfigInfo) dao.getObjectByQuery(query,AgentConfigInfo.class);
	}

	@SuppressWarnings("unchecked")
	public List<AgentConfigInfo> getAllAgentConfigInfoList(CommonDAO dao, int startIndex, int totalRecords) {
		String query = "SELECT s FROM AgentConfigInfo s ";
		return (List<AgentConfigInfo>) (List<?>) dao.getAllObjectsForPaging(query, AgentConfigInfo.class,startIndex,totalRecords);
	}

	public long getAgentConfigInfoCount(CommonDAO dao) {
		String query = "SELECT COUNT(agentCID) AS COUNT FROM AgentConfigInfo ";
		return (Long) dao.getObjectByQuery(query,Long.class);
	}

	@SuppressWarnings("unchecked")
	public List<AgentConfigInfo> getAllAgentConfigInfoListBYDate(CommonDAO dao, String colsName, Object value) {

		String query = "SELECT p FROM AgentConfigInfo p where " + colsName + " ='" + value + "'";
		return (List<AgentConfigInfo>) (List<?>) dao.getObjectByQuery(query, AgentConfigInfo.class);
	}

	public AgentConfigInfo handleAgentConfigInfo(CommonDAO dao,String agentId,ServiceClient serviceClient) {

		AgentConfigInfo agent = getAgentConfigInfoById(dao, agentId);

		if (agent == null || agent.getAgentId() == null) {
			agent = serviceClient.getAgentByIdDB(agentId);
			agent.setCarrierId(agent.getCarrierId());
			dao.addObject(agent);

		} else {
			dao.updateByObject(agent);
		}
		return agent;
	}

	public int getAgentCIDbyAgentId(CommonDAO dao, String agentId) {
		
			try{
				String query = "select agentCID from AgentConfigInfo where AGENTID='" + agentId + "'";
				return (Integer) dao.getObjectByQuery(query,Integer.class);
			}catch(Exception e){log.error(e);}
			return 0;
		
	}
	
	
	
}