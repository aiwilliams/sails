package org.opensails.viento;

import java.util.List;


public class DefaultExceptionHandler implements ExceptionHandler {
	public Object resolutionFailed(String methodName, Object[] args, List<Throwable> failedAttempts) {
		throw new ResolutionFailedException(null, methodName, args, failedAttempts);
	}

	public Object resolutionFailed(Object target, String methodName, Object[] args, List<Throwable> failedAttempts) {
		throw new ResolutionFailedException(target, methodName, args, failedAttempts);
	}
}