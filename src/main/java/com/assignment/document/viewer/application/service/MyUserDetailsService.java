package com.assignment.document.viewer.application.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 
 * @author Santosh Gadekar
 * 
 * 
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		/* 
		 * This method can be extended to get credentials from DB or from third party client like OAuth/openId
		 * and pass to User object as per below line
		 * For simplicity I have used one user with username/password as credentials
		*/
		return new User("username", "passcode", new ArrayList());
	}

}
