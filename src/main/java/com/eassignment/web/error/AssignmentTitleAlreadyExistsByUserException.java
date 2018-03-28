package com.eassignment.web.error;

public class AssignmentTitleAlreadyExistsByUserException extends RuntimeException{

	private static final long serialVersionUID = -3159088991612780406L;

	public AssignmentTitleAlreadyExistsByUserException() {
		super();
	}

	public AssignmentTitleAlreadyExistsByUserException(String message, Throwable cause) {
		super(message, cause);
	}

	public AssignmentTitleAlreadyExistsByUserException(String message) {
		super(message);
	}

	public AssignmentTitleAlreadyExistsByUserException(Throwable cause) {
		super(cause);
	}

}
