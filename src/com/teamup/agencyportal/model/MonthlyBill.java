package com.teamup.agencyportal.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;
@Scope("session")
@Entity
@Table(name = "MonthlyBill") 
public class MonthlyBill  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	
	@Id
	int agentCID;
	@Id
	int month;
	@Id
	int year;
	@Id
	String type;
	
	long amount;
	long dueDate;
	long creationDt;
	long transationDt;
	
	@Transient
	String agentId;
	@Transient
	int transCount;
	
	public int getTransCount() {
		return transCount;
	}
	public void setTransCount(int transCount) {
		this.transCount = transCount;
	}
	
	public int getAgentCID() {
		return agentCID;
	}
	public void setAgentCID(int agentCID) {
		this.agentCID = agentCID;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	
	public long getCreationDt() {
		return creationDt;
	}
	public void setCreationDt(long creationDt) {
		this.creationDt = creationDt;
	}
	public long getTransationDt() {
		return transationDt;
	}
	public void setTransationDt(long transationDt) {
		this.transationDt = transationDt;
	}
	public long getDueDate() {
		return dueDate;
	}
	public void setDueDate(long dueDate) {
		this.dueDate = dueDate;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
}
