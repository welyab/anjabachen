package com.welyab.anjabachen.perft;

import com.welyab.anjabachen.ChessException;

public class PerftException extends ChessException {
	
	public PerftException() {
		super();
	}
	
	public PerftException(String message) {
		super(message);
	}
	
	public PerftException(Throwable cause) {
		super(cause);
	}
	
	public PerftException(String message, Throwable cause) {
		super(message, cause);
	}
}
