package org.opensails.sails.adapter;

import org.opensails.sails.*;

/**
 * Thrown by {@link org.opensails.sails.adapter.IAdapter}'s when they are
 * unable to adapt objects for the web or for the model.
 * 
 * @author aiwilliams
 */
public class AdaptationException extends SailsException {
	private static final long serialVersionUID = 3228078495740352118L;

	public AdaptationException() {
		super("Failure adapting object");
	}
    public AdaptationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AdaptationException(String message) {
        super(message);
    }

    public AdaptationException(Throwable cause) {
        super(cause);
    }
}
