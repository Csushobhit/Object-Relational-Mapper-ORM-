package com.yourcompany.simpleorm.exception;


public class PersistanceException extends OrmException{
	public PersistanceException(String message)
	{
		super(message);
	}
	
	public PersistanceException(String message, Throwable cause) {
		super(message, cause);
	}
}