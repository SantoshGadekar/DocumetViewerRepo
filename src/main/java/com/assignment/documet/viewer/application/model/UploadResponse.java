package com.assignment.documet.viewer.application.model;


/**
 * 
 * @author Santosh Gadekar
 * Model object returned back if file upload was successful.
 *
 */
public class UploadResponse {
	
	private String fileName;
	private String filePath;
	private String fileContentType;
	private Long fileSize;
	private String status;
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileContentType() {
		return fileContentType;
	}
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}
	public Long getFileSize() {
		return fileSize;
	}
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}
