package org.opensails.sails.controller.annotate;

import java.lang.annotation.Annotation;

import org.opensails.sails.controller.IAction;
import org.opensails.sails.controller.IControllerImpl;

public class AnnotationContext {
	protected final Class<? extends IControllerImpl> implementationClass;

	public AnnotationContext(Class<? extends IControllerImpl> implementationClass) {
		this.implementationClass = implementationClass;
	}

	public Annotation[] behaviors(IAction action) {
		return null;
	}

	public Annotation[] indicators(IAction action) {
		return null;
	}
}
