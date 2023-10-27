package com.kb.exceptions;

class ExistingUserException extends RuntimeException {

	public ExistingUserException(String msg) {
		super(msg);
	}
}

class UserNotFoundException extends RuntimeException {
	public UserNotFoundException(String msg) {
		super(msg);
	}
}

class WrongPasswordException extends RuntimeException {
	public WrongPasswordException(String msg) {
		super(msg);
	}
}

class InsufficientBalanceException extends RuntimeException {
	public InsufficientBalanceException(String msg) {
		super(msg);
	}
}

public class CustomException {

	public void existingUserExcep() {
		throw new ExistingUserException("User Already Exist");
	}

	public void UserNotFoundException() {
		throw new UserNotFoundException("User Not Found !!!!");
	}

	public void wrongPasswordException() {
		throw new WrongPasswordException("Please Enter Correct Password And ID !!!!");
	}

	public void insufficientBalanceException() {
		throw new InsufficientBalanceException("Insufficient Balance !!!!");
	}

}
