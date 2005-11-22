package org.opensails.viento;


public class ResolutionFailedException extends RuntimeException {
	private final Object[] args;
	private final String methodName;
	private final Object target;

	public ResolutionFailedException(Object target, String methodName, Object[] args) {
		this(null, target, methodName, args);
	}

	public ResolutionFailedException(Throwable exception, Object target, String methodName, Object[] args) {
		super(exception);
		this.target = target;
		this.methodName = methodName;
		this.args = args;
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

		return buffer.toString();
	}
}
