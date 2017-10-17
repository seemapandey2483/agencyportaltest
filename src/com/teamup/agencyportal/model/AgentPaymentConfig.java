package com.teamup.agencyportal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
@Scope("session")
@Entity
@Table(name = "AgentPaymentConfig") 
public class AgentPaymentConfig {
	@Column(updatable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@Column(updatable = false)
	int agentCID;
	@Column(updatable = false)
	String type;
	@Column(updatable = false)
	String lastFourDigit;
	@Column(updatable = false)
	String expiryDate;
	@Column(updatable = false)
	String securityCode;
	@Column(updatable = false)
	String cardType;
	@Column(updatable = false)
	String bankName;
	@Column(updatable = false)
	String accountNumber;
	@Column(updatable = false)
	String routingNumber;
	@Column(updatable = false)
	String accountType;
	@Column(updatable = false)
	String active;
	@Column(updatable = false)
	Date creationDt;
	@Column(updatable = false)
	String name;
	String agree;
	@Column(updatable = false)
	String accontId;
	@Column(updatable = false)
	String customerId;
	
	public String getAccontId() {
		return accontId;
	}
	public void setAccontId(String accontId) {
		this.accontId = accontId;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLastFourDigit() {
		return lastFourDigit;
	}
	public void setLastFourDigit(String lastFourDigit) {
		this.lastFourDigit = lastFourDigit;
	}
	
	public String getSecurityCode() {
		return securityCode;
	}
	public void setSecurityCode(String securityCode) {
		this.securityCode = securityCode;
	}
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getRoutingNumber() {
		return routingNumber;
	}
	public void setRoutingNumber(String routingNumber) {
		this.routingNumber = routingNumber;
	}
	public String getAccountType() {
		return accountType;
	}
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	
	public Date getCreationDt() {
		return creationDt;
	}
	public void setCreationDt(Date creationDt) {
		this.creationDt = creationDt;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getAgree() {
		return agree;
	}
	public void setAgree(String agree) {
		this.agree = agree;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	
}
