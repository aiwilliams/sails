package org.opensails.prevayler.commands;

import java.util.Date;

import org.opensails.prevayler.IdentifiableObjectPrevalentSystem;
import org.opensails.sails.persist.IIdentifiable;
import org.prevayler.Transaction;

public class DestroyCommand implements Transaction {

	private final IIdentifiable object;

	public DestroyCommand(IIdentifiable object) {
		this.object = object;
	}

	public void executeOn(Object prevalentSystem, Date executionTime) {
		IdentifiableObjectPrevalentSystem objects = (IdentifiableObjectPrevalentSystem) prevalentSystem;
		objects.remove(object);
	}

}
