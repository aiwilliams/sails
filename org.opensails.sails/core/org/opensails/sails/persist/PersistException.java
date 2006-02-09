package org.opensails.sails.persist;

import org.opensails.sails.SailsException;

public class PersistException extends SailsException {
	private static final long serialVersionUID = 4607833091801857355L;

	public PersistException(String message) {
		super(message);
	}

	public PersistException(Throwable cause) {
		super(cause);
	}

	public PersistException(String message, Throwable cause) {
		super(message, cause);
	}

}
