package com.teamup.agencyportal.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
@Scope("session")
@Entity
@Table(name = "Deposit") 
public class Deposit implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	int id;
	
	int agentCID;
	int month;
	int year;
	String type;
	
	long amount;
	String status;
	long creationDt	;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getCreationDt() {
		return creationDt;
	}
	public void setCreationDt(long creationDt) {
		this.creationDt = creationDt;
	}
	
}
