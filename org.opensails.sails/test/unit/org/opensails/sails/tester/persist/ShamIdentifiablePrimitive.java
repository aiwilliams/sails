package org.opensails.sails.tester.persist;

import org.opensails.sails.persist.IIdentifiable;

public class ShamIdentifiablePrimitive implements IIdentifiable {
	private long id;

	public Long getId() {
		return id;
	}
}
