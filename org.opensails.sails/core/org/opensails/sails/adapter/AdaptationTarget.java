package org.opensails.sails.adapter;

public class AdaptationTarget {
	protected Class targetClass;

	public AdaptationTarget(Class targetClass) {
		this.targetClass = targetClass;
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

}
