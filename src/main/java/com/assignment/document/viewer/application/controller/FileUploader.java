package com.assignment.document.viewer.application.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.assignment.document.viewer.application.service.FileManagementService;
import com.assignment.documet.viewer.application.model.UploadResponse;


/**
 * 
 * @author Santosh Gadekar
 * 
 * This controller exposes end points for file upload operation.
 *
 */
@RestController
public class FileUploader {
	
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(FileUploader.class);
	
	@Autowired
	private FileManagementService fileManagementService;
	
	@GetMapping("/hello")
	public String hello() {
		return "hello World";
	}
	
	@PostMapping("/uploadFile")
	private UploadResponse uploadFile(@RequestParam("filePath") MultipartFile file) {
		
		logger.debug("entered into uploadFile method");
		
		String fileName = fileManagementService.storeFile(file);
		
		String fileDownloadPath = ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("")
				.path(fileName)
				.toString();
		
		UploadResponse response = new UploadResponse();
		response.setStatus("success");
		response.setFileName(fileName);
		response.setFilePath(fileDownloadPath.toString());
		response.setFileContentType(file.getContentType());
		response.setFileSize(file.getSize());
		
		logger.debug("exiting from uploadFile method");
		
		return response;
	}
	
	@PostMapping("/uploadFiles")
	private List<UploadResponse> uploadFiles(@RequestParam("files")MultipartFile[] files){
		logger.debug("entered into uploadFiles method");
		return Arrays.asList(files)
				.stream()
				.map(file -> uploadFile(file))
				.collect(Collectors.toList());
	}
}
