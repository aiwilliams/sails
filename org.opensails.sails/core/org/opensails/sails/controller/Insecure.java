package org.opensails.sails.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.opensails.sails.controller.annotate.Indicator;
import org.opensails.sails.controller.oem.SecurityIndicatorHandler;

@Indicator(SecurityIndicatorHandler.class)
@Target( { ElementType.TYPE, ElementType.METHOD })
public @interface Insecure {}
