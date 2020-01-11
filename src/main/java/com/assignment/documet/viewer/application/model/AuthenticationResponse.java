package com.assignment.documet.viewer.application.model;


/**
 * 
 * @author Santosh Gadekar
 * 
 *  This class represents Authentication response with JWT string.
 *
 */
public class AuthenticationResponse {
	
	private String jwt;

	
	
	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
	
}
