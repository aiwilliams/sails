package org.opensails.sails.adapter;

import java.lang.reflect.Type;

public class AdaptationTarget<TC> {
	protected Class<TC> targetClass;
	protected Type targetGenericType;

	public AdaptationTarget(Class<TC> targetClass) {
		this.targetClass = targetClass;
	}

	public AdaptationTarget(Class<TC> targetClass, Type targetGenericType) {
		this(targetClass);
		this.targetGenericType = targetGenericType;
	}

	public boolean exists() {
		return targetClass != null;
	}

	/**
	 * @return the class of the field or property
	 */
	public Class<TC> getTargetClass() {
		return targetClass;
	}

	/**
	 * @return the generic type of the field or property, as in MyThing when
	 *         source declares Collection<MyThing>, or null if not applicable
	 */
	public Type getTargetGenericType() {
		return targetGenericType;
	}

	public boolean isReadable() {
		return targetClass != null;
	}

}
