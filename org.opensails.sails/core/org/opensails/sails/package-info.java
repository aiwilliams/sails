package org.opensails.sails;

/**
 * The Sails Web Application Framework classes and interfaces that represent the
 * 'entry points' of it's archetecture.
 * <h1>Welcome to Sails</h1>
 * <h2>Getting Started</h2>
 * TODO: Document Getting Started
 * <p>
 * <h4>Some gritty details</h4>
 * Sails is essentially a Servlet application. HTTP events are transformed into
 * ISailsEvents of various kinds, then dispatched to the appropriate
 * IActionEventProcessor (controller, component). The processor typically
 * delegates to an IAction, usually implemented as a method on an
 * IEventProcessingContext (a controller or component implementation
 * class).
 */
