package org.opensails.spyglass.policy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public abstract class MethodInvocationPolicy {
	public static MethodInvocationPolicy PRIVATE = new MethodInvocationPolicy() {
		@Override
		public boolean canInvoke(Method method) {
			return true;
		}

		@Override
		public String getDescription() {
			return "public, protected and private";
		}
	};
	public static MethodInvocationPolicy PROTECTED = new MethodInvocationPolicy() {
		@Override
		public boolean canInvoke(Method method) {
			return Modifier.isPublic(method.getModifiers()) || Modifier.isProtected(method.getModifiers());
		}

		@Override
		public String getDescription() {
			return "public and protected";
		}
	};
	public static MethodInvocationPolicy PUBLIC = new MethodInvocationPolicy() {
		@Override
		public boolean canInvoke(Method method) {
			return Modifier.isPublic(method.getModifiers());
		}

		@Override
		public String getDescription() {
			return "public only";
		}
	};

	public abstract boolean canInvoke(Method method);

	/**
	 * This describes what the policy will allow.
	 * <p>
	 * Dynamic policies should give descriptions that will make sense of their
	 * programmatic rules.
	 * 
	 * @return a description of this policy
	 */
	public abstract String getDescription();

	@Override
	public String toString() {
		return String.format("Method: %s", getDescription());
	}
}
