package org.opensails.sails.tester.persist;

import java.util.Date;

import org.opensails.sails.persist.IIdentifiable;

public class ShamDatedIdentifiable implements IIdentifiable {
	private Long id;
	protected Date aDate;
	protected String first;

	public ShamDatedIdentifiable(String first, Date aDate) {
		this.first = first;
		this.aDate = aDate;
	}

	public Long getId() {
		return id;
	}
}
