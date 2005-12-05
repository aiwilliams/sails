package org.opensails.sails.controller.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
public @interface Indicator {
	Class<? extends IIndicatorHandler> value();
}
