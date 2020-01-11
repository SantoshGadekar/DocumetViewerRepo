package com.assignment.document.viewer.application;

import org.slf4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.assignment.document.viewer.application.controller.AuthenticationController;

/**
 * 
 * @author Santosh Gadekar
 *
 */
@SpringBootApplication
public class DocumentViewerApplication {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(DocumentViewerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(DocumentViewerApplication.class, args);
		logger.info("Document viewer applcation is running");
	}

}
