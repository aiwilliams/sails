package org.opensails.sails.adapter;

import org.opensails.sails.*;

/**
 * Thrown by {@link org.opensails.sails.adapter.IAdapter}'s when they are
 * unable to adapt objects for the web or for the model.
 * 
 * @author aiwilliams
 */
public class AdaptationException extends SailsException {
	public AdaptationException() {
		super("Failure adapting object");
	}
}
