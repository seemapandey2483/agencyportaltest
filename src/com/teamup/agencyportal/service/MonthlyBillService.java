package com.teamup.agencyportal.service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.teamup.agencyportal.dao.CommonDAO;
import com.teamup.agencyportal.model.MonthlyBill;
@Component
@Repository
public class MonthlyBillService {

	 Logger log = Logger.getLogger(getClass());
	
	public boolean saveMonthlyReportsBILL(CommonDAO dao,List<MonthlyBill> reports,AgentConfigInfoService agentConfigInfoService) {

		try {
			ListIterator<MonthlyBill> litr = reports.listIterator();
			while (litr.hasNext()) {
				Map<?, ?> map = (Map<?, ?>) litr.next();
				int agentcid = agentConfigInfoService.getAgentCIDbyAgentId(dao,"" + map.get("agentId"));
				if (agentcid != 0 ) {
						MonthlyBill monthlyBill = new MonthlyBill();
						monthlyBill.setAgentCID(agentcid);
						monthlyBill.setMonth((Integer) map.get("month"));
						monthlyBill.setYear((Integer) map.get("year"));
						monthlyBill.setType("I");
						monthlyBill.setAmount(0);
						if (map.get("DUEDATE") != null)
							monthlyBill.setDueDate((Long) map.get("DUEDATE"));
						else
							monthlyBill.setDueDate((Long) System.currentTimeMillis());
						
						monthlyBill.setCreationDt((Long) map.get("creationDt"));
						monthlyBill.setTransationDt((Long) map.get("transationDt"));
							try{
								dao.addObject(monthlyBill);
							}catch(Exception e){log.error(e);}
				  }
			     
		     }
		} catch (Exception e) {
			log.error(e);
		}

		return true;

	}

	@SuppressWarnings("unchecked")
	public List<MonthlyBill> getListMonthlyBILL(CommonDAO dao, int month,int year,String status) {
		String query = "select p from MonthlyBill p where month="+month+" and year="+year;
		if(status!=null)
			query = query + " and type='"+status+"'";
		List<MonthlyBill> monthlyBillList = null;
		try{
			monthlyBillList = (List<MonthlyBill>) (List<?>) dao.getAllObjectsByQuery(query, MonthlyBill.class);
		}catch(Exception e){log.error(e);}
		return monthlyBillList;
	}

	
	public List<MonthlyBill> getMonthlyReportsList(CommonDAO dao, int month, int year,int startIndex,int total) {
		int endIndex = startIndex*total+total;
		List<MonthlyBill> list = getMonthalyReportsList(dao,month,year);
		list = list.subList(startIndex*total, endIndex > list.size() ? list.size() : endIndex);
		return list;
	}

	public long getMonthlyBillCount(CommonDAO dao, int month,	int year) {
		String query = "SELECT COUNT(id) AS COUNT FROM MonthlyBill where month = '" + month	+ "' and year= '" + year + "'";
		return (Long) dao.getObjectByQuery(query,Long.class);
	
	}

	@SuppressWarnings("rawtypes")
	public List<MonthlyBill> getMonthalyReportsPayment(CommonDAO dao, int month, int year) {

				List result = getMonthalyReportsForDeposit(dao,month,year);
				List<MonthlyBill> listm =  new ArrayList<>();
				for (Object p : result)
					  listm.add(setResult(p,month,year));
				return listm;
	}
	
	@SuppressWarnings("rawtypes")
	public  List getMonthalyReportsForDeposit(CommonDAO dao, int month, int year) {

		String query = "select agentCID,transationDt,(sum(case when type ='D' THEN amount ELSE 0 END )-sum(case when type ='I' THEN amount ELSE 0 END )) as amount from MonthlyBill m where month='"+month+"' and year='"+year+ "' group by agentCID,transationDt";
		return dao.getAllObjectsByQuery(query,null);
	 }
	
	public List<MonthlyBill> getMonthalyReportsList(CommonDAO dao, int month, int year) {

		String query = "select agentCID,transationDt,amount from MonthlyBill m where month='"+month+"' and year='"+year+ "' and Type='D'";
		List result = dao.getAllObjectsByQuery(query,null);
		List<MonthlyBill> listm =  new ArrayList<>();
		for (Object p : result)
			  listm.add(setResult(p,month,year));
		log.info(listm.size());
		return listm;
   }
	
	private MonthlyBill setResult(Object result,int month,int year) {
		MonthlyBill mBill = new MonthlyBill();
		   if (result instanceof Object[]) {
		      Object[] row = (Object[]) result;
		      mBill.setAgentCID((Integer)row[0]);
		      mBill.setTransationDt((Long)row[1]);
		      mBill.setAmount((Long)row[2]);
		      mBill.setCreationDt(System.currentTimeMillis());
		      mBill.setType("D");
		      mBill.setMonth(month);
		      mBill.setYear(year);
		    }
		   return mBill;
		 }
}