package com.teamup.agencyportal.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.model.Deposit;
@Component
@Repository
public class DepositService {

	 Logger log = Logger.getLogger(getClass());
	@SuppressWarnings("rawtypes")
	public boolean saveDepositList(CommonDAO dao,List list,int month,int year) {
		List<Deposit> listm =  new ArrayList<>();
			for (Object p : list) 
			  listm.add(setResult(p));
			for (Deposit deposit : listm) {
				deposit.setMonth(month);
				deposit.setYear(year);
				deposit.setStatus("P");
				deposit.setType("I");
				deposit.setCreationDt(System.currentTimeMillis());
				dao.addObject(deposit);
		}
		return false;
	}
	
	public Deposit getDeposit(CommonDAO dao, String agentCid,int month, int year) {

		String query = "SELECT p FROM deposit p  where AGENTCID='" + agentCid + "' MONTH='" + month + "' YEAR='" + year + "";
		return (Deposit) dao.getObjectByQuery(query,Deposit.class);
		
	}
	
	 private Deposit setResult(Object result) {
		 Deposit deposit = new Deposit();
		   if (result instanceof Object[]) {
		      Object[] row = (Object[]) result;
		      deposit.setAgentCID((Integer)row[0]);
		      deposit.setAmount((Long)row[2]);
		    }
		   return deposit;
		 }
}