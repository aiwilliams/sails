package org.opensails.spyglass.policy;

public abstract class ImmutablePolicy extends SpyPolicy {
	@Override
	public void setFieldAccessPolicy(FieldAccessPolicy policy) {
		throw new ImmutablePolicyException();
	}
}
