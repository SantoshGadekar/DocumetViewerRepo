package com.assignment.document.viewer.application.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * 
 * @author Santosh Gadekar
 * 
 * This class services to store and load provided file
 *
 */
@Service
@ConfigurationProperties(prefix = "file")
@PropertySource("classpath:application.properties")
public class FileManagementService {
	
	private String uploadDir;

	public String getUploadDir() {
		return uploadDir;
	}

	public void setUploadDir(String uploadDir) {
		this.uploadDir = uploadDir;
	}	
	
	public String storeFile(MultipartFile file) {
		// TODO Auto-generated method stub
		
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		
		Path path = Paths.get(getUploadDir()).toAbsolutePath().normalize();
		
		try {
			Files.createDirectories(path);
			Path targetLocation = path.resolve(fileName);
			Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return fileName;
	}

	public Resource loadResourceWithFileName(String fileName) {
		// TODO Auto-generated method stub
		try {
			Path filePath = Paths.get(getUploadDir()).resolve(fileName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if(resource.exists()) {
				return resource;
			} else {
				throw new com.assignment.document.viewer.application.exception.FileNotFoundException("Request file could not be found"+fileName);
			}
			
		} catch(MalformedURLException e) {
				throw new com.assignment.document.viewer.application.exception.FileNotFoundException("Request file could not be found"+fileName, e);
		}
	
	}

}
