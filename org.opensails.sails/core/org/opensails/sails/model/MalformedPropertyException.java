package org.opensails.sails.model;

import org.opensails.sails.SailsException;

public class MalformedPropertyException extends SailsException {
	private static final long serialVersionUID = 2438926769927460317L;

	public MalformedPropertyException(String message) {
		super(message);
	}
}
