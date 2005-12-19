package org.opensails.sails.annotate.oem;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

import org.opensails.sails.annotate.IIndicatorHandler;
import org.opensails.sails.controller.IControllerImpl;

public class SecurityIndicatorHandler implements IIndicatorHandler {
	public void handle(Annotation indicator, ElementType type, IControllerImpl controller) {}
}