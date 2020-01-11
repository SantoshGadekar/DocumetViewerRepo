package com.assignment.document.viewer.application.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import com.assignment.document.viewer.application.service.MyUserDetailsService;
import com.assignment.document.viewer.application.utils.JWTUtils;
import com.assignment.documet.viewer.application.model.AuthenticationRequest;

import junit.framework.Assert;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class AuthenticationControllerTest {

	@MockBean
	private AuthenticationManager authenticationManager;
	
	@MockBean
	private MyUserDetailsService userDetailsService;
	
	@MockBean
	private JWTUtils jwtUtils;
	
	@Autowired
	private TestRestTemplate restTemplate;
	     
	@LocalServerPort
	int randomServerPort;
	
	@Test
	public void should_Generate_Token_And_Verify() throws Exception {
	      
		final String baseUrl = "http://localhost:"+randomServerPort+"/authenticate";
		
		URI uri = new URI(baseUrl);
	
		AuthenticationRequest requestModel = new AuthenticationRequest();
		requestModel.setUsername("username");
		requestModel.setPassword("password");
		
		String mockJWT = "eyJhbGciOiJub25lIn0.eyJzdWIiOiJ1c2VybmFtZSIsImV4cCI6MTU3ODczMTgzMywiaWF0IjoxNTc4NzMxNDcyfQ.";
		
		User user = new User("username", "passcode", new ArrayList());
		
		Mockito.when(userDetailsService.loadUserByUsername(requestModel.getUsername())).thenReturn(user);
		
		Mockito.when(jwtUtils.generateToken(user)).thenReturn(mockJWT);
		
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<AuthenticationRequest> request = new HttpEntity<>(requestModel, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		System.out.println("result is "+result.getBody().toString());
		
		Assert.assertEquals(200, result.getStatusCodeValue());	
		
		assertEquals(true, result.getBody().contains("eyJhbGciOiJub25lIn0.eyJzdWIiOiJ1c2VybmFtZSIsImV4cCI6MTU3ODczMTgzMywiaWF0IjoxNTc4NzMxNDcyfQ."));
	}
	
	@Test
	public void should_Failed_with_different_token() throws Exception {
	      
		final String baseUrl = "http://localhost:"+randomServerPort+"/authenticate";
		
		URI uri = new URI(baseUrl);
	
		AuthenticationRequest requestModel = new AuthenticationRequest();
		requestModel.setUsername("username");
		requestModel.setPassword("password");
		
		String mockJWT = "eyJhbGciOiJub25lIn0.eyJzdWIiOiJ1c2VybmFtZSIsImV4cCI6MTU3ODczMTgzMywiaWF0IjoxYTc4NzMxNDcyfQ.";
		
		User user = new User("username", "password", new ArrayList());
		
		Mockito.when(userDetailsService.loadUserByUsername(requestModel.getUsername())).thenReturn(user);
		
		Mockito.when(jwtUtils.generateToken(user)).thenReturn(mockJWT);
		
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<AuthenticationRequest> request = new HttpEntity<>(requestModel, headers);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);

		System.out.println("result is "+result.getBody().toString());
		
		Assert.assertEquals(200, result.getStatusCodeValue());	
		
		assertEquals(false, result.getBody().contains("eyJhbGciOiJub25lIn0.eyJzdWIiOiJ1c2VybmFtZSIsImV4cCI6MTU3ODczMTgzMywiaWF0IjoxNTc4NzMxNDcyfQ."));
	}
}
