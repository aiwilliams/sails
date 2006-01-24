package org.opensails.viento;



public class DefaultExceptionHandler implements ExceptionHandler {
	public Object resolutionFailed(TargetedMethodKey key, Object target, Object[] args, int line, int offset) {
		throw new ResolutionFailedException(target, key.methodName, args, line, offset);
	}

	public Object resolutionFailed(TopLevelMethodKey key, Object[] args, int line, int offset) {
		throw new ResolutionFailedException(null, key.methodName, args, line, offset);
	}

	public Object resolutionFailed(Throwable exception, TopLevelMethodKey key, Object[] args, int line, int offset) {
		throw new ResolutionFailedException(exception, null, key.methodName, args, line, offset);
	}

	public Object resolutionFailed(Throwable exception, TargetedMethodKey key, Object target, Object[] args, int line, int offset) {
		throw new ResolutionFailedException(exception, target, key.methodName, args, line, offset);
	}

	public Object nullTarget(String methodName, Object[] args, int line, int offset) {
		throw new NullPointerException("Viento line: " + line + ", offset: " + offset);
	}
}