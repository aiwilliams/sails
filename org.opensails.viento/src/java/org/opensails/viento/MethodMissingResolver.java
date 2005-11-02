package org.opensails.viento;

import java.lang.reflect.Method;


public class MethodMissingResolver extends ObjectMethods {
	
	public CallableMethod find(TargetedMethodKey key) {
		try {
			Method methodMissing = key.targetClass.getDeclaredMethod("methodMissing", new Class[] { String.class, Object[].class });
			return new MethodMissingMethod(methodMissing, key.methodName);
		} catch (Exception e) {
			return null;
		}
	}
	
	public class MethodMissingMethod extends ObjectMethod {
		private final String methodName;

		public MethodMissingMethod(Method method, String methodName) {
			super(method);
			this.methodName = methodName;
		}
		
		@Override
		public Object call(Object target, Object[] args) {
			return super.call(target, new Object[] { methodName, args });
		}
	}
}
