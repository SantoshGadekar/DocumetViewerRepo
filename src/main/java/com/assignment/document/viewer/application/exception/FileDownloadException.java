package com.assignment.document.viewer.application.exception;


/**
 * 
 * @author Santosh Gadekar
 *
 * This exception can be used during file download operation related errors
 */
public class FileDownloadException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileDownloadException(String message) {
		super(message);
	}
	
	public FileDownloadException(String message, Throwable ex) {
		super(message, ex);
	}
}
