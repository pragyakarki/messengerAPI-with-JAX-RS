package org.karkixpragya.messenger.exception;

public class DataNotFoundException extends RuntimeException {
	// every Exceptions need serialVersionID
	private static final long serialVersionUID = -3754859758255975289L;
	
	public DataNotFoundException(String message) {
		super(message);
	}
}
