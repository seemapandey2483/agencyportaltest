package com.teamup.agencyportal.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.context.annotation.Scope;

@Scope("session")
@Entity
@Table(name = "AgentConfigInfo") 
public class AgentConfigInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(updatable = false)
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int agentCID	;
	@Column(updatable = false)
	String carrierId;
	@Column(updatable = false)
	String agentId;
	@Column(updatable = false)
	String agencyName;
	@Column(updatable = false)
	String name;
	@Column(updatable = false)
	String email;
	@Column(updatable = false)
	String phone;
	@Column(updatable = false)
	String city	;
	@Column(updatable = false)
	String address1;
	@Column(updatable = false)
	String address2;
	@Column(updatable = false)
	String state;
	@Column(updatable = false)
	String zip;
	long lastDownloadDt	;
	long lastPaymentDt;   
	long lastPaymentAmt	;
	@Transient
	String password;
	
	public int getAgentCID() {
		return agentCID;
	}
	public void setAgentCID(int agentCID) {
		this.agentCID = agentCID;
	}
	public String getCarrierId() {
		return carrierId;
	}
	public void setCarrierId(String carrierId) {
		this.carrierId = carrierId;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public long getLastDownloadDt() {
		return lastDownloadDt;
	}
	public void setLastDownloadDt(long lastDownloadDt) {
		this.lastDownloadDt = lastDownloadDt;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public long getLastPaymentAmt() {
		return lastPaymentAmt;
	}
	public void setLastPaymentAmt(long lastPaymentAmt) {
		this.lastPaymentAmt = lastPaymentAmt;
	}
	public long getLastPaymentDt() {
		return lastPaymentDt;
	}
	public void setLastPaymentDt(long lastPaymentDt) {
		this.lastPaymentDt = lastPaymentDt;
	}
	
}
