/**
 * 
 */
package org.opensails.viento;

import java.lang.reflect.Method;

public class TopLevelMixin extends ObjectMethod {
	private final Object mixin;

	public TopLevelMixin(Method method, Object mixin) {
		super(method);
		this.mixin = mixin;
	}

	public Object call(Object target, Object[] args) {
		return super.call(mixin, args);
	}
}