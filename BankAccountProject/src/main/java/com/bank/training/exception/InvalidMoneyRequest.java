package com.bank.training.exception;

public class InvalidMoneyRequest extends RuntimeException {

	private static final long serialVersionUID = 3726165107390954836L;

	public InvalidMoneyRequest(String message) {
		super(message);
	}
}
