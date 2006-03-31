package org.opensails.spyglass.policy;

import org.opensails.sails.SailsException;

public class ImmutablePolicyException extends SailsException {
	private static final long serialVersionUID = -240719625534115846L;

	public ImmutablePolicyException() {
		super("This policy cannot be modified");
	}
}
