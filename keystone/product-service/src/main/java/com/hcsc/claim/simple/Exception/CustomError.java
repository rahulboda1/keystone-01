package com.hcsc.claim.simple.Exception;

/**
 * @author Rahul
 *
 */
public class CustomError {
	
	private String name;
	private String message;
	
	public CustomError() {
		super();
	}
	
	
	/**
	 * @param name
	 * @param message
	 */
	public CustomError(String name, String message) {
		this();
		this.name = name;
		this.message = message;
	}
	
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
