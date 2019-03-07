package com.mercury.exceptions;

public class ConflictException extends RuntimeException{
	public ConflictException(String message) { super(message); }
	public ConflictException() { super(); }
	private static final long serialVersionUID = 1L;
}