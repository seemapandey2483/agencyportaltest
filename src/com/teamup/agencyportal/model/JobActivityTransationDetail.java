package com.teamup.agencyportal.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
@Scope("session")
@Entity
@Table(name = "JobActivityTransationDetail") 
public class JobActivityTransationDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int jactId;
	int agentCId;
	long amount;
	String lastFourDigit;
	String routingNumber;
	long creationDt;
	long txnDt;
	String description;
	String status;
	String transactionId;
	String chargeId;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getAgentCId() {
		return agentCId;
	}
	public void setAgentCId(int agentCId) {
		this.agentCId = agentCId;
	}
	
	public String getLastFourDigit() {
		return lastFourDigit;
	}
	public void setLastFourDigit(String lastFourDigit) {
		this.lastFourDigit = lastFourDigit;
	}
	public String getRoutingNumber() {
		return routingNumber;
	}
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}
	public long getCreationDt() {
		return creationDt;
	}
	public void setCreationDt(long creationDt) {
		this.creationDt = creationDt;
	}
	public long getTxnDt() {
		return txnDt;
	}
	public void setTxnDt(long txnDt) {
		this.txnDt = txnDt;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public int getJactId() {
		return jactId;
	}
	public void setJactId(int jactId) {
		this.jactId = jactId;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getChargeId() {
		return chargeId;
	}
	public void setChargeId(String chargeId) {
		this.chargeId = chargeId;
	}
	
	
	
}
