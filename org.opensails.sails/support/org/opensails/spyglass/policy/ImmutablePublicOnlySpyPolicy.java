package org.opensails.spyglass.policy;

public class ImmutablePublicOnlySpyPolicy extends SpyPolicy {
	protected ImmutablePublicOnlySpyPolicy() {
		fieldAccessPolicy = FieldAccessPolicy.PUBLIC;
		methodInvocationPolicy = MethodInvocationPolicy.PUBLIC;
	}
}
