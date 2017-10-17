package com.teamup.agencyportal.service;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.teamup.agencyportal.constant.AgencyPortalConstant;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.model.UserConfig;
@Component
@Repository
public class UserService {
	 Logger log = Logger.getLogger(getClass());

	public UserConfig getUser(CommonDAO dao, String userName) {

		String query = "select p from UserConfig p where userName=" + userName;
		log.info(query);
		return (UserConfig) dao.getObjectByQuery(query, UserConfig.class);
	}

	public boolean validateUser(CommonDAO dao, String userName,String password) {
		String query = "select p from UserConfig p where userName='" + userName + "' and password='" + password + "'";
		if (dao.getObjectByQuery(query, UserConfig.class) != null)
			return true;
		else
			return false;
	}
	
	public UserConfig addDefaultUser(CommonDAO dao) {

		String query = "select p from UserConfig p ";
		UserConfig user = (UserConfig) dao.getObjectByQuery(query, UserConfig.class);
		if(user==null || user.getUserName()==null){
			UserConfig uConfig = new UserConfig();
			uConfig.setUserName(AgencyPortalConstant.ADMIN_USER);
			uConfig.setPassword(AgencyPortalConstant.ADMIN_PASSWORD);
			dao.addObject(uConfig);
		}
		return user;
	}
}