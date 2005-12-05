package org.opensails.sails.controller.annotate;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

import org.opensails.sails.controller.IControllerImpl;

public interface IBehaviorHandler {
	void handle(Annotation behavior, ElementType type, IControllerImpl controller);
}
