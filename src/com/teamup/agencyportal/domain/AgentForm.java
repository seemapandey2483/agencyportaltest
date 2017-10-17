package com.teamup.agencyportal.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.teamup.agencyportal.model.AgentConfigInfo;
import com.teamup.agencyportal.model.AgentPaymentConfig;
import com.teamup.agencyportal.model.JobActivity;
import com.teamup.agencyportal.model.JobActivityTransationDetail;
import com.teamup.agencyportal.model.JobsDetail;
import com.teamup.agencyportal.model.MonthlyBill;

public class AgentForm {
	
	  Set<MonthlyBill> monthlyBills = new HashSet<>();

	  List<AgentConfigInfo> listAgents = new ArrayList<>();
	
	  List<JobsDetail> listJobsDetails = new ArrayList<>();

	  List<JobActivity> listActivity = new ArrayList<>();
	
	  List<JobActivityTransationDetail> listActivitysTrans = new ArrayList<>();
	
	  List<AgentPaymentConfig> listPaymentConfig = new ArrayList<>();
	
	  AgentConfigInfo agentConfigInfo ;
	
	public List<JobsDetail> getListJobsDetails() {
		return listJobsDetails;
	}

	public void setListJobsDetails(List<JobsDetail> listJobsDetails) {
		this.listJobsDetails = listJobsDetails;
	}

	public Set<MonthlyBill> getMonthlyBills() {
		return monthlyBills;
	}

	public void setMonthlyBills(Set<MonthlyBill> monthlyBills) {
		this.monthlyBills = monthlyBills;
	}

	public List<AgentConfigInfo> getListAgents() {
		return listAgents;
	}

	public void setListAgents(List<AgentConfigInfo> listAgents) {
		this.listAgents = listAgents;
	}

	public List<JobActivity> getListActivity() {
		return listActivity;
	}

	public void setListActivity(List<JobActivity> listActivity) {
		this.listActivity = listActivity;
	}

	public List<JobActivityTransationDetail> getListActivitysTrans() {
		return listActivitysTrans;
	}

	public void setListActivitysTrans(
			List<JobActivityTransationDetail> listActivitysTrans) {
		this.listActivitysTrans = listActivitysTrans;
	}

	public List<AgentPaymentConfig> getListPaymentConfig() {
		return listPaymentConfig;
	}

	public void setListPaymentConfig(List<AgentPaymentConfig> listPaymentConfig) {
		this.listPaymentConfig = listPaymentConfig;
	}

	public AgentConfigInfo getAgentConfigInfo() {
		return agentConfigInfo;
	}

	public void setAgentConfigInfo(AgentConfigInfo agentConfigInfo) {
		this.agentConfigInfo = agentConfigInfo;
	}

}
