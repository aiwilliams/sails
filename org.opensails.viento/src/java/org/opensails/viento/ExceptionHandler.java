package org.opensails.viento;

import java.util.List;


public interface ExceptionHandler {
	Object resolutionFailed(String methodName, Object[] args, List<Throwable> failedAttempts);
	Object resolutionFailed(Object target, String methodName, Object[] args, List<Throwable> failedAttempts);
}
