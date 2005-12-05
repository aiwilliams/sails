package org.opensails.sails.controller.oem;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.controller.annotate.IIndicatorHandler;

public class SecurityIndicatorHandler implements IIndicatorHandler {
	public void handle(Annotation indicator, ElementType type, IControllerImpl controller) {}
}