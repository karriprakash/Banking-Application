package com.bank.training.exception;

public class AccountNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4760649373539324636L;

	public AccountNotFoundException(String message) {
		super(message);
	}
}
