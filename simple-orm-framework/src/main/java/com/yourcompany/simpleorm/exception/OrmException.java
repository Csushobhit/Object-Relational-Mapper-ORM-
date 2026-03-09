package com.yourcompany.simpleorm.exception;

public class OrmException extends RuntimeException{
	public OrmException(String message)
	{
		super(message);
	}
	
	public OrmException(String message, Throwable cause) {
		super(message, cause);
	}
}
