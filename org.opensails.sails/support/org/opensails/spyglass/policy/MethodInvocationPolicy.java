package org.opensails.spyglass.policy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class MethodInvocationPolicy {
	public static MethodInvocationPolicy PRIVATE = new MethodInvocationPolicy() {
		@Override
		public boolean canInvoke(Method method) {
			return true;
		}
	};
	public static MethodInvocationPolicy PROTECTED = new MethodInvocationPolicy() {
		@Override
		public boolean canInvoke(Method method) {
			return method.isAccessible() || Modifier.isProtected(method.getModifiers());
		}
	};
	public static MethodInvocationPolicy PUBLIC = new MethodInvocationPolicy() {
		@Override
		public boolean canInvoke(Method method) {
			return method.isAccessible();
		}
	};

	public abstract boolean canInvoke(Method method);
}
