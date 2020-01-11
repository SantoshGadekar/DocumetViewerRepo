package com.assignment.document.viewer.application.exception;

/**
 * 
 * @author sgadekar
 * 
 * This exception class is used for throwing file not found exception.
 *
 */

public class FileNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public FileNotFoundException(String message) {
		super(message);
	}
	
	public FileNotFoundException(String message, Throwable ex) {
		super(message, ex);
	}
}
