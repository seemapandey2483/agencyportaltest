package com.teamup.agencyportal.domain;

public class AuthTokenInfo {

	protected String accessToken;
	String tokenType;
	String refreshToken;
	int expiresIn;
	String scope;
	
	protected String getAccessToken() {
		return accessToken;
	}
	protected void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	@Override
	public String toString() {
		return "AuthTokenInfo [accessToken=" + accessToken + ", tokenType=" + tokenType + ", refreshToken="
				+ refreshToken + ", expiresIn=" + expiresIn + ", scope=" + scope + "]";
	}
	
	
}
