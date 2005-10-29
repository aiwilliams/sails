package org.opensails.viento;

import java.util.List;

public class ResolutionFailedException extends RuntimeException {
	private final Object[] args;
	private final String methodName;
	private final Object target;
	private final List<Throwable> failedAttempts;

	public ResolutionFailedException(Object target, String methodName, Object[] args, List<Throwable> failedAttempts) {
		this.target = target;
		this.methodName = methodName;
		this.args = args;
		this.failedAttempts = failedAttempts;
	}

	@Override
	public String getMessage() {
		StringBuilder buffer = new StringBuilder();
		if (target != null)
			buffer.append("Could not resolve method <");
		else
			buffer.append("Could not resolve reference <");
		buffer.append(methodName);
		buffer.append("(");
		for (int i = 0; i < args.length; i++) {
			if (i > 0) buffer.append(", ");
			if (args[i] instanceof Block)
				buffer.append("Block");
			else
				buffer.append(args[i]);
		}
		buffer.append(")>");
		if (target != null) {
			buffer.append(" for type <");
			buffer.append(target.getClass());
			buffer.append(">");
		}
		
		buffer.append("\nFailed attempts:");
		for (Throwable throwable : failedAttempts) {
			buffer.append("\n");
			buffer.append(throwable);
		}
		
		return buffer.toString();
	}
}
