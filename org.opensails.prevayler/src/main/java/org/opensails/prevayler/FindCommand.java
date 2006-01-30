package org.opensails.prevayler;

import java.util.Date;

import org.prevayler.TransactionWithQuery;

public class FindCommand implements TransactionWithQuery {

	private final Class objectType;

	private final Long id;

	public FindCommand(Class objectType, Long id) {
		this.objectType = objectType;
		this.id = id;
	}

	public FindCommand(Class objectType) {
		this.objectType = objectType;
		this.id = null;
	}

	public Object executeAndQuery(Object prevalentSystem, Date executionTime) throws Exception {
		IdentifiableObjectPrevalentSystem objects = (IdentifiableObjectPrevalentSystem) prevalentSystem;
		if (id == null)
			return objects.getAll(objectType);
		else
			return objects.get(objectType, id);
	}

}
