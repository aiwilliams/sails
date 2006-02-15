package org.opensails.viento;

public class UnresolvableObject implements MethodMissing {
	private final ExceptionHandler exceptionHandler;
	private final Throwable exception;
	private final MethodKey key;
	private final Object target;
	private final Object[] args;
	private final int line;
	private final int offset;
	private final boolean wasNull;

	public UnresolvableObject(ExceptionHandler exceptionHandler, Throwable exception, MethodKey key, Object target, Object[] args, int line, int offset, boolean wasNull) {
		this.exceptionHandler = exceptionHandler;
		this.exception = exception;
		this.key = key;
		this.target = target;
		this.args = args;
		this.line = line;
		this.offset = offset;
		this.wasNull = wasNull;
	}

	public String causeMessage() {
		if (wasNull)
			return String.format("%s:null", key);
		if (exception != null)
			return String.format("%s:%s", key, exception.getMessage());
		return String.format("%s:resolution failure", key);
	}

	public Object methodMissing(String methodName, Object[] args) {
		// TODO keep track of these things?
		return this;
	}

	@Name("?") public Object silence() {
		return new SilencedObject();
	}

	@Override public String toString() {
		// ugly.
		if (wasNull)
			return String.valueOf(exceptionHandler.nullTarget(((TopLevelMethodKey) key).methodName, args, line, offset));
		if (exception == null) {
			if (key instanceof TargetedMethodKey)
				return String.valueOf(exceptionHandler.resolutionFailed((TargetedMethodKey) key, target, args, line, offset));
			return String.valueOf(exceptionHandler.resolutionFailed((TopLevelMethodKey) key, args, line, offset));
		}
		if (key instanceof TargetedMethodKey)
			return String.valueOf(exceptionHandler.resolutionFailed(exception, (TargetedMethodKey) key, target, args, line, offset));
		return String.valueOf(exceptionHandler.resolutionFailed(exception, (TopLevelMethodKey) key, args, line, offset));
	}

	public static class SilencedObject implements MethodMissing {
		public Object methodMissing(String methodName, Object[] args) {
			return this;
		}

		@Override public String toString() {
			return "";
		}
	}
}
