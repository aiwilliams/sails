package org.opensails.viento;

import java.util.Map;

/**
 * Makes for a convenient IBinding tree root.
 * 
 * @author aiwilliams
 */
public class NullBinding implements IBinding {

	public IBinding createChild() {
		return new Binding(this);
	}

	public CallableMethod find(TopLevelMethodKey key) {
		return null;
	}

	public ExceptionHandler getExceptionHandler() {
		return new DefaultExceptionHandler();
	}

	public void mixin(Class<?> target, Object mixin) {
		throw new UnsupportedOperationException();
	}

	public void mixin(Object mixin) {
		throw new UnsupportedOperationException();
	}

	public void put(String key, Object object) {
		throw new UnsupportedOperationException();
	}

	public void putAll(Map<String, Object> map) {
		throw new UnsupportedOperationException();
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		throw new UnsupportedOperationException();
	}

	public CallableMethod find(TargetedMethodKey key) {
		return null;
	}

	public void addMethodResolver(IMethodResolver resolver) {
		throw new UnsupportedOperationException();
	}

}
