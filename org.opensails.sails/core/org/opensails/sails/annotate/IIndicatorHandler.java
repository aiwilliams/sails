package org.opensails.sails.annotate;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

import org.opensails.sails.controller.IControllerImpl;

public interface IIndicatorHandler {
	void handle(Annotation indicator, ElementType type, IControllerImpl controller);
}
