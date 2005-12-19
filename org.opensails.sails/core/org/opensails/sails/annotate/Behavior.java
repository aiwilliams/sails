package org.opensails.sails.annotate;

public @interface Behavior {
	Class<? extends IBehaviorHandler> behavior();
}
