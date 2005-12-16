package org.opensails.viento;

import java.util.Map;

public interface IBinding {

	void mixin(Class<?> target, Object mixin);

	void mixin(Object mixin);

	void put(String key, Object object);

	void setExceptionHandler(ExceptionHandler exceptionHandler);

	void putAll(Map<String, Object> map);

}