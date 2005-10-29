package org.opensails.sails.template;

import java.util.List;

public interface IExceptionHandler {
	Object resolutionFailed(String methodName, Object[] args, List<Throwable> failedAttempts);
	Object resolutionFailed(Object target, String methodName, Object[] args, List<Throwable> failedAttempts);
}
