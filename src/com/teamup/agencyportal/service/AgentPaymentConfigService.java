package com.teamup.agencyportal.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.model.AgentPaymentConfig;
@Component
@Repository
public class AgentPaymentConfigService {
	 Logger log = Logger.getLogger(getClass());

	public AgentPaymentConfig getPaymentInfo(CommonDAO dao,int agentCId, int agree) {

		String query = "SELECT p FROM AgentPaymentConfig p where agentCID='" + agentCId + "' and agree='" + agree + "'";
		return (AgentPaymentConfig) dao.getObjectByQuery(query,AgentPaymentConfig.class);
	}

	public int getPaymentId(CommonDAO dao, int agentCId, int agree) {
		int id = 0 ;
		try{
			String query = "select id from AgentPaymentConfig a where agentCID=" + agentCId + " and agree='" + agree + "'";
			id  = (Integer) dao.getObjectByQuery(query,Integer.class);
		}catch(Exception e){
			log.error(e);
			return id ;
		}
		return id;
	}

	public boolean isPaymentInfoExist(CommonDAO dao, int agentCId,String colsName, String colsValue) {

		String query = "select p from AgentPaymentConfig p where agentCID='" + agentCId + "' and " + colsName + "='" + colsValue + "'";
		AgentPaymentConfig agentPaymentConfig = (AgentPaymentConfig) dao.getObjectByQuery(query,AgentPaymentConfig.class);
		if (agentPaymentConfig == null || agentPaymentConfig.getAccountNumber()== null)
			return false;
		else
			return true;
		
	}

	public  boolean savePaymentInfo(CommonDAO dao,AgentPaymentConfig paymentInfo) {
		boolean isExist = false;
		
		if (paymentInfo.getAccountNumber() != null) {
			isExist = isPaymentInfoExist(dao, paymentInfo.getAgentCID(),"accountNumber", paymentInfo.getAccountNumber());
		} else {
			isExist = isPaymentInfoExist(dao, paymentInfo.getAgentCID(),"routingNumber", paymentInfo.getAccountNumber());
		}
		
		if (!isExist) {
			List<AgentPaymentConfig> listPaymentConfig = getListPaymentConfig(dao ,paymentInfo.getAgentCID());
			if (listPaymentConfig != null && listPaymentConfig .isEmpty() ) {
				for(AgentPaymentConfig pConfig: listPaymentConfig){
					pConfig.setAgentCID(paymentInfo.getAgentCID());
				    pConfig.setAgree("0");
				    dao.updateByObject(pConfig);
				}
			}
			paymentInfo.setAgree("1");
			dao.addObject(paymentInfo);
			isExist = true;
		}
		return isExist;
	}

	@SuppressWarnings("unchecked")
	public List<AgentPaymentConfig> getListPaymentConfig(CommonDAO dao, int agentId) {
		String query = "select p from AgentPaymentConfig p where agentCID="	+ agentId;
		List<AgentPaymentConfig> listPaymentConfig = null;
		try{
			listPaymentConfig = (List<AgentPaymentConfig>) (List<?>) dao.getAllObjectsByQuery(query, AgentPaymentConfig.class);
		}catch(Exception e){log.error(e);}
		return listPaymentConfig;
	}

	public AgentPaymentConfig getPaymentConfig(CommonDAO dao,int agentId, int id) {
		String query = "select p from AgentPaymentConfig p where agentCID='" + agentId + "' and id='" + id + "'";
		return (AgentPaymentConfig) dao.getObjectByQuery(query,AgentPaymentConfig.class);
	}
	
}