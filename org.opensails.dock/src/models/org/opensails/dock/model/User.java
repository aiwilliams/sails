package org.opensails.dock.model;

import org.opensails.sails.persist.IIdentifiable;

public class User implements IIdentifiable {
	protected String firstName;

	public String getFirstName() {
		return firstName;
	}

	public Long getId() {
		return 1234l;
	}
}
