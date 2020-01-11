package com.assignment.document.viewer.application.controller;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.junit.Test;
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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import com.assignment.document.viewer.application.service.FileManagementService;
import com.assignment.document.viewer.application.service.MyUserDetailsService;
import com.assignment.document.viewer.application.utils.JWTUtils;
import junit.framework.Assert;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.RANDOM_PORT)
public class FileUploaderTest {

	@MockBean
	private FileManagementService fileManagementService;
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@MockBean
	private MyUserDetailsService myUserDetailsService;
	     
	@LocalServerPort
	int randomServerPort;
	
	@MockBean
	private JWTUtils jwtUtils;
	
	
	@Test
	public void should_upload_file() throws URISyntaxException {
		
		final String baseUrl = "http://localhost:"+randomServerPort+"/uploadFile";
		
		String username = "username";
		
		String jwt = "eyJhbGciOiJub25lIn0.eyJzdWIiOiJ1c2VybmFtZSIsImV4cCI6MTU3ODczMTgzMywiaWF0IjoxNTc4NzMxNDcyfQ.";
		
		String fileName = "test.txt";
		
		User user = new User("username", "passcode", new ArrayList());
		
		Mockito.when(fileManagementService.getUploadDir()).thenReturn("./upload");
		
		File file = new File(fileManagementService.getUploadDir() + fileName);
		
		MockMultipartFile mockMultipartFile = new MockMultipartFile("filePath", fileName,
	              "text/plain", "test data".getBytes());
		
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer "+jwt);

		HttpEntity<?> request = new HttpEntity<>(mockMultipartFile, headers);
		
		Mockito.when(jwtUtils.extractUsername("username")).thenReturn(jwt);
		
		Mockito.when(jwtUtils.validateToken(jwt, user)).thenReturn(true);
		
		Mockito.when(myUserDetailsService.loadUserByUsername(username)).thenReturn(user);

		ResponseEntity<String> result = this.restTemplate.postForEntity(uri, request, String.class);
		
		Assert.assertTrue(file.exists());
		
	}
}
