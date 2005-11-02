package org.opensails.viento;



public class DefaultExceptionHandler implements ExceptionHandler {
	public Object resolutionFailed(TargetedMethodKey key, Object target, Object[] args) {
		throw new ResolutionFailedException(target, key.methodName, args);
	}

	public Object resolutionFailed(TopLevelMethodKey key, Object[] args) {
		throw new ResolutionFailedException(null, key.methodName, args);
	}
}