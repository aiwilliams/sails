package org.opensails.sails.annotate.oem;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import org.opensails.sails.annotate.Indicator;

@Indicator(SecurityIndicatorHandler.class)
@Target( { ElementType.TYPE, ElementType.METHOD })
public @interface Insecure {}
