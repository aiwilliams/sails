package org.opensails.viento;



public interface ExceptionHandler {
	Object resolutionFailed(TargetedMethodKey key, Object target, Object[] args);
	Object resolutionFailed(TopLevelMethodKey key, Object[] args);
	Object resolutionFailed(Throwable exception, TopLevelMethodKey key, Object[] args);
	Object resolutionFailed(Throwable exception, TargetedMethodKey key, Object target, Object[] args);
}
