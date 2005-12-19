package org.opensails.sails.annotate;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

public class BehaviorInstance<B extends Annotation> {
	private final B annotation;
	private final ElementType type;

	public BehaviorInstance(B annotation, ElementType type) {
		this.annotation = annotation;
		this.type = type;
	}

	public B getAnnotation() {
		return annotation;
	}

	public ElementType getElementType() {
		return type;
	}
}
