package org.opensails.sails.persist;

import org.opensails.sails.SailsException;

public class PersistException extends SailsException {
	public PersistException(String message) {
		super(message);
	}

	public PersistException(Throwable cause) {
		super(cause);
	}
}
