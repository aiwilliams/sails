package org.opensails.functional.controllers;

import junit.framework.Assert;

import org.opensails.sails.event.ISailsEvent;

public class EventTestSubclassController extends EventTestController {
	public EventTestSubclassController(ISailsEvent event) {
		Assert.assertNotNull("You can access the event in the constructor if you declare the dependency. Anything else in the container, too", event);
	}
}
