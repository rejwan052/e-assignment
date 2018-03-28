package com.eassignment.web.error;

public final class GroupNameAlreadyExistsException extends RuntimeException{

	private static final long serialVersionUID = 5996383072179114466L;

	public GroupNameAlreadyExistsException() {
		super();
	}

	public GroupNameAlreadyExistsException(String message, Throwable cause) {
		super(message, cause);
	}

	public GroupNameAlreadyExistsException(String message) {
		super(message);
	}

	public GroupNameAlreadyExistsException(Throwable cause) {
		super(cause);
	}
	
	

}
