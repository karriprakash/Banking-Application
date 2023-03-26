package com.bank.training.exception;

public class EmptyUserObject extends RuntimeException {

	private static final long serialVersionUID = -3440968934481693518L;

	public EmptyUserObject(String message) {
		super(message);
	}
}
