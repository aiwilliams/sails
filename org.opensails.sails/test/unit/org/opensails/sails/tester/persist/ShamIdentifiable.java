package org.opensails.sails.tester.persist;

import org.opensails.sails.persist.IIdentifiable;

public class ShamIdentifiable implements IIdentifiable {
	private Long id;
	protected String first;
	protected String last;

	public ShamIdentifiable() {}

	public ShamIdentifiable(String first) {
		this.first = first;
	}

	public ShamIdentifiable(String first, String last) {
		this.first = first;
		this.last = last;
	}

	public Long getId() {
		return id;
	}

	public void setName(String first) {
		this.first = first;
	}
}
