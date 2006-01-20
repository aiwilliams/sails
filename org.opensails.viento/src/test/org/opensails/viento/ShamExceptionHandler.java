package org.opensails.viento;

public class ShamExceptionHandler implements ExceptionHandler {

	public Object resolutionFailed(TargetedMethodKey key, Object target, Object[] args, int line, int offset) {
		return "here";
	}

	public Object resolutionFailed(TopLevelMethodKey key, Object[] args, int line, int offset) {
		return "here";
	}

	public Object resolutionFailed(Throwable exception, TopLevelMethodKey key, Object[] args, int line, int offset) {
		return "here";
	}

	public Object resolutionFailed(Throwable exception, TargetedMethodKey key, Object target, Object[] args, int line, int offset) {
		return "here";
	}
}
