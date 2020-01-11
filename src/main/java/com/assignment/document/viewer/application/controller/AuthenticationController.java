package com.assignment.document.viewer.application.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.document.viewer.application.utils.JWTUtils;
import com.assignment.documet.viewer.application.model.AuthenticationRequest;
import com.assignment.documet.viewer.application.model.AuthenticationResponse;

/**
 * 
 * @author Santosh Gadekar
 * 
 * This controller is used for authenticating user and generating JWT out of provided credentials.
 *
 */
@RestController
public class AuthenticationController {

	Logger logger = org.slf4j.LoggerFactory.getLogger(AuthenticationController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequest authRequest) {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		} catch (Exception e) {
			logger.error("invalid credentials details provided");
		}
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
		
		final String jwt = jwtUtils.generateToken(userDetails);
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
