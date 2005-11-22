package org.opensails.sails.template;


public interface IExceptionHandler {
	Object resolutionFailed(String methodName, Object[] args);
	Object resolutionFailed(Object target, String methodName, Object[] args);
	Object resolutionFailed(Throwable cause, String methodName, Object[] args);
	Object resolutionFailed(Throwable cause, Object target, String methodName, Object[] args);
}
