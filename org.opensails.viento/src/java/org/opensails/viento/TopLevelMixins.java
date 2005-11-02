package org.opensails.viento;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TopLevelMixins extends ObjectMethods {
	protected List<Object> mixins = new ArrayList<Object>();
	
	public CallableMethod find(TopLevelMethodKey key) {
		for (Object mixin : mixins) {
			Method method = findAppropriateMethod(mixin.getClass(), key.methodName, key.argClasses);
			if (method != null)
				return new TopLevelMixin(method, mixin);
		}
		return null;
	}
	
	public void add(Object mixin) {
		mixins.add(mixin);
	}

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
}
