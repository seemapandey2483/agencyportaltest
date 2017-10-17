package com.teamup.agencyportal.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
@Scope("session")
@Entity
@Table(name = "JobsDetail") 
public class JobsDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String jobId;
	String jobName;
	String jobDesc;
	String jobGrpName;
	String jobTrigName;
	String jobTrigStr;
	String jobClassName;
	String active;
	String lastRunStatus;
	Date lastRunDate;
	@Transient
	String startFlag;
	
	public String getStartFlag() {
		return startFlag;
	}
	public void setStartFlag(String startFlag) {
		this.startFlag = startFlag;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobDesc() {
		return jobDesc;
	}
	public void setJobDesc(String jobDesc) {
		this.jobDesc = jobDesc;
	}
	public String getJobGrpName() {
		return jobGrpName;
	}
	public void setJobGrpName(String jobGrpName) {
		this.jobGrpName = jobGrpName;
	}
	public String getJobTrigName() {
		return jobTrigName;
	}
	public void setJobTrigName(String jobTrigName) {
		this.jobTrigName = jobTrigName;
	}
	
	public String getJobClassName() {
		return jobClassName;
	}
	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getLastRunStatus() {
		return lastRunStatus;
	}
	public void setLastRunStatus(String lastRunStatus) {
		this.lastRunStatus = lastRunStatus;
	}
	public Date getLastRunDate() {
		return lastRunDate;
	}
	
	public void setLastRunDate(Date lastRunDate) {
	   if(lastRunDate==null)
		  lastRunDate = new Date(System.currentTimeMillis());
		this.lastRunDate = lastRunDate;
	}
	public String getJobTrigStr() {
		return jobTrigStr;
	}
	public void setJobTrigStr(String jobTrigStr) {
		this.jobTrigStr = jobTrigStr;
	}
	
	
	
	
}
