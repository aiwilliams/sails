package org.opensails.sails.controller.annotate;

public @interface Behavior {
	Class<? extends IBehaviorHandler> behavior();
}
