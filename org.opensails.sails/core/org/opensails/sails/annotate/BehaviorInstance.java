package org.opensails.sails.annotate;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

public class BehaviorInstance<B extends Annotation> {
	private final B annotation;
	private final Behavior behaviorAnnotation;
	private final ElementType type;

	public BehaviorInstance(B annotation, ElementType type) {
		this.annotation = annotation;
		this.behaviorAnnotation = annotation.annotationType().getAnnotation(Behavior.class);
		this.type = type;
	}

	@Override
	public boolean equals(Object obj) {
		return annotation.annotationType() == ((BehaviorInstance) obj).annotation.annotationType();
	}

	public B getAnnotation() {
		return annotation;
	}

	public Class<? extends IBehaviorHandler> getBehaviorHandlerClass() {
		return behaviorAnnotation.behavior();
	}

	public ElementType getElementType() {
		return type;
	}

	@Override
	public int hashCode() {
		return annotation.annotationType().hashCode();
	}
}
