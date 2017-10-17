package com.teamup.agencyportal.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.context.annotation.Scope;
@Scope("session")
@Entity
@Table(name = "ConfigDownload") 
public class ConfigDownload {
	@Id
	String agentId;
	long lastDownload;
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	public long getLastDownload() {
		return lastDownload;
	}
	public void setLastDownload(long lastDownload) {
		this.lastDownload = lastDownload;
	}
	
}
