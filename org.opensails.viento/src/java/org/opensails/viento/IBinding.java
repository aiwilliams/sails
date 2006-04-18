package org.opensails.viento;

import java.util.Map;

public interface IBinding extends IMethodResolver, IObjectResolver {

	void mixin(Class<?> target, Object mixin);

	void mixin(Object mixin);

	void put(String key, Object object);

	void addMethodResolver(IMethodResolver resolver);

	void setExceptionHandler(ExceptionHandler exceptionHandler);

	ExceptionHandler getExceptionHandler();

	void putAll(Map<String, Object> map);

	IBinding createChild();

}