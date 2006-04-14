package org.opensails.spyglass.policy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SpyPolicy {
	/**
	 * A SpyPolicy that provides practically no access security.
	 */
	public static final SpyPolicy WIDEOPEN = new ImmutableWideOpenSpyPolicy();

	/**
	 * A SpyPolicy that provides a medium level of access security.
	 */
	public static SpyPolicy MEDIUM = new ImmutableMediumSpyPolicy();

	protected FieldAccessPolicy fieldAccessPolicy = FieldAccessPolicy.PUBLIC;
	protected MethodInvocationPolicy methodInvocationPolicy = MethodInvocationPolicy.PUBLIC;

	public boolean canAccess(Field field) {
		return fieldAccessPolicy.canAccess(field);
	}

	public boolean canInvoke(Method method) {
		return methodInvocationPolicy.canInvoke(method);
	}

	public FieldAccessPolicy getFieldAccessPolicy() {
		return fieldAccessPolicy;
	}

	public MethodInvocationPolicy getMethodInvocationPolicy() {
		return methodInvocationPolicy;
	}

	public void setFieldAccessPolicy(FieldAccessPolicy policy) {
		this.fieldAccessPolicy = policy;
	}

	public void setMethodInvocationPolicy(MethodInvocationPolicy methodInvocationPolicy) {
		this.methodInvocationPolicy = methodInvocationPolicy;
	}

	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(fieldAccessPolicy);
		s.append("\n");
		s.append(methodInvocationPolicy);
		return s.toString();
	}
}
