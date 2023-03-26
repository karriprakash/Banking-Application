package com.bank.training.exception;

public class UserNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6623975326264548127L;

	public UserNotFoundException(String message) {
		super(message);
	}
}
