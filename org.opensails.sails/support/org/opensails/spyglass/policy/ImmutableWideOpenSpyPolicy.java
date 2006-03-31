package org.opensails.spyglass.policy;

public class ImmutableWideOpenSpyPolicy extends ImmutablePolicy {
	protected ImmutableWideOpenSpyPolicy() {
		fieldAccessPolicy = FieldAccessPolicy.PRIVATE;
		methodInvocationPolicy = MethodInvocationPolicy.PRIVATE;
	}
}