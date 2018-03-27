package com.hcsc.claim.module1.validate;

public class CustomError {
	
	private String name;
	private String message;
	
	public CustomError() {
		super();
	}
	
	public CustomError(String name, String message) {
		this();
		this.name = name;
		this.message = message;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
