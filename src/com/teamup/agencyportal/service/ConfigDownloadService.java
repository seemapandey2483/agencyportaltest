package com.teamup.agencyportal.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.teamup.agencyportal.constant.AgencyPortalConstant;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.model.ConfigDownload;
@Component
@Repository
public class ConfigDownloadService {

	 Logger log = Logger.getLogger(getClass());

	public long getConfigDate(CommonDAO cm, String agentId) {
			
			long datetime = 0 ;
			
			try{
				String query = "select max(lastDownload)  as maxd from ConfigDownload where AGENTID='"	+ agentId + "'";
				datetime = (Long) cm.getObjectByQuery(query,Long.class);
			}catch(Exception e){
				log.error(e);
				return System.currentTimeMillis();
			}
			
			if (datetime == 0)
				return System.currentTimeMillis();
		
			return datetime;
	}
	public ConfigDownload addConfigDate(CommonDAO dao) {

		String query = "select p from ConfigDownload p ";
		ConfigDownload configDownload = (ConfigDownload) dao.getObjectByQuery(query, ConfigDownload.class);
		if(configDownload==null || configDownload.getLastDownload()==0){
			configDownload = new ConfigDownload();
			configDownload.setAgentId(AgencyPortalConstant.CARRIER_ID);
			configDownload.setLastDownload(AgencyPortalConstant.CARRIER_ID_LAST_DOWNLOAD_DATE);
			dao.addObject(configDownload);
		}
		return configDownload;
	}

}