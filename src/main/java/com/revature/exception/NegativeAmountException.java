package com.revature.exception;

public class NegativeAmountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3682839471080765518L;

	public NegativeAmountException(String message){
		super(message);
	}
}
