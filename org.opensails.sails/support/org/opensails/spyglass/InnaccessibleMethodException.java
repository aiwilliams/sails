package org.opensails.spyglass;

import org.opensails.sails.SailsException;
import org.opensails.spyglass.policy.MethodInvocationPolicy;

public class InnaccessibleMethodException extends SailsException {
	private static final long serialVersionUID = 1909089742272199601L;

	public InnaccessibleMethodException(String methodName, Object[] args, MethodInvocationPolicy policy) {
		super(String.format("Method [%s] inaccessible for policy [%s]", methodName, policy));
	}

}
