package org.impactindia.llemeddocket.orm;

public class DAOException extends Exception {

	public DAOException(Exception e) {
		super(e);
	}
	
	@Override
	public String getMessage() {
		String message = getCause().getMessage();
		if (message == null) {
			message = getCause().toString();
		}
		return message;
	}
}
