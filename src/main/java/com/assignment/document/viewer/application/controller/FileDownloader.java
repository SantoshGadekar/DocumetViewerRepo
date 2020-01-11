package com.assignment.document.viewer.application.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.document.viewer.application.service.FileManagementService;

/**
 * 
 * @author Santosh Gadekar
 *
 * This controller exposes end points for file download operation.
 */

@RestController
public class FileDownloader {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(FileDownloader.class);
	
	@Autowired
	private FileManagementService fileManagementService;
	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletRequest request) {
		
		logger.debug("entered into downloadFile method");
		
		Resource resource = fileManagementService.loadResourceWithFileName(fileName);
		
		String contentType = null;
		
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
		} catch (IOException e) {
			logger.error("error caught while getting content type of file during downloading"+fileName);
		}
		
		if(contentType == null) {
					contentType = "application/octet-steam";
		}
		
		logger.debug("exiting from downloadFile method");
		
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION	, "attachment; filename=\""+ resource.getFilename())
				.body(resource);
	}
}
