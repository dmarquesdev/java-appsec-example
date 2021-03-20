package com.appsec.java.exception;

public class DAOException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5763610489946153530L;

	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(String message, Throwable e) {
		super(message, e);
	}
}
