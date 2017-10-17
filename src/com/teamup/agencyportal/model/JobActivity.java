package com.teamup.agencyportal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
@Scope("session")
@Entity
@Table(name = "JobActivity") 
public class JobActivity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String jobId;
	long jobEndTime;
	int noOfTransactionFailed;
	int noOfTransactionSuccess;
	String status;
	long jobStartTime;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getNoOfTransactionFailed() {
		return noOfTransactionFailed;
	}
	public void setNoOfTransactionFailed(int noOfTransactionFailed) {
		this.noOfTransactionFailed = noOfTransactionFailed;
	}
	public int getNoOfTransactionSuccess() {
		return noOfTransactionSuccess;
	}
	public void setNoOfTransactionSuccess(int noOfTransactionSuccess) {
		this.noOfTransactionSuccess = noOfTransactionSuccess;
	}
	public String getJobId() {
		return jobId;
	}
	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	public long getJobEndTime() {
		return jobEndTime;
	}
	public void setJobEndTime(long jobEndTime) {
		this.jobEndTime = jobEndTime;
	}
	public long getJobStartTime() {
		return jobStartTime;
	}
	public void setJobStartTime(long jobStartTime) {
		this.jobStartTime = jobStartTime;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
