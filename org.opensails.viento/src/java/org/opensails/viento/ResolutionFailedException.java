package org.opensails.viento;


@SuppressWarnings("serial")
public class ResolutionFailedException extends RuntimeException {
	private final Object[] args;
	private final String methodName;
	private final Object target;
	public final int line;
	public final int offset;

	public ResolutionFailedException(Object target, String methodName, Object[] args, int line, int offset) {
		this(null, target, methodName, args, line, offset);
	}

	public ResolutionFailedException(Throwable exception, Object target, String methodName, Object[] args, int line, int offset) {
		super(exception);
		this.target = target;
		this.methodName = methodName;
		this.args = args;
		this.line = line;
		this.offset = offset;
	}

	@Override
	public String getMessage() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("Line: ");
		buffer.append(line);
		buffer.append(", Offset: ");
		buffer.append(offset);
		if (target != null)
			buffer.append(", Could not resolve method <");
		else
			buffer.append(", Could not resolve reference <");
		buffer.append(methodName);
		buffer.append("(");
		for (int i = 0; i < args.length; i++) {
			if (i > 0) buffer.append(", ");
			if (args[i] instanceof Block)
				buffer.append("Block");
			else if (args[i] instanceof String)
				buffer.append(String.format("'%s'", args[i]));
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
