package com.bank.training.exception;

public class InsufficientFundsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5791606763616408008L;

	public InsufficientFundsException(String message) {
		super(message);
	}
}
