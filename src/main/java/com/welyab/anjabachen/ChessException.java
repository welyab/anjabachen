package com.welyab.anjabachen;

public class ChessException extends RuntimeException {
	
	public ChessException() {
	}
	
	public ChessException(String message) {
		super(message);
	}
	
	public ChessException(Throwable cause) {
		super(cause);
	}
	
	public ChessException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
