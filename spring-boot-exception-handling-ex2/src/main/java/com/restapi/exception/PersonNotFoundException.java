package com.restapi.exception;

public class PersonNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PersonNotFoundException(String msg) {
		super(msg);
	}
}
