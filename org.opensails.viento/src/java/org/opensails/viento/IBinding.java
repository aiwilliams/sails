package org.opensails.viento;

public interface IBinding {

	void mixin(Class<?> target, Object mixin);

	void mixin(Object mixin);

	void put(String key, Object object);

	void setExceptionHandler(ExceptionHandler exceptionHandler);

}