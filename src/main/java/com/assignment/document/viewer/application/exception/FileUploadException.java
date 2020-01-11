package com.assignment.document.viewer.application.exception;


/**
 * 
 * @author Santosh Gadekar
 * 
 * This exception can be used during file upload operation related errors
 *
 */
public class FileUploadException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileUploadException(String message) {
		super(message);
	}
	
	public FileUploadException(String message, Throwable ex) {
		super(message, ex);
	}
}
