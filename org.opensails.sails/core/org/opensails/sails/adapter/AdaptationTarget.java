package org.opensails.sails.adapter;

public class AdaptationTarget<TC> {
	protected Class<TC> targetClass;

	public AdaptationTarget(Class<TC> targetClass) {
		this.targetClass = targetClass;
	}

	public Class<TC> getTargetClass() {
		return targetClass;
	}

}
