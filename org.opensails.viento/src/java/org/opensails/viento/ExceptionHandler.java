package org.opensails.viento;



public interface ExceptionHandler {
	Object resolutionFailed(TargetedMethodKey key, Object target, Object[] args, int line, int offset);
	Object resolutionFailed(TopLevelMethodKey key, Object[] args, int line, int offset);
	Object resolutionFailed(Throwable exception, TopLevelMethodKey key, Object[] args, int line, int offset);
	Object resolutionFailed(Throwable exception, TargetedMethodKey key, Object target, Object[] args, int line, int offset);
}
