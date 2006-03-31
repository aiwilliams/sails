package org.opensails.spyglass.policy;

public class ImmutableMediumSpyPolicy extends ImmutablePolicy {
	protected ImmutableMediumSpyPolicy() {
		fieldAccessPolicy = FieldAccessPolicy.PROTECTED;
		methodInvocationPolicy = MethodInvocationPolicy.PROTECTED;
	}
}