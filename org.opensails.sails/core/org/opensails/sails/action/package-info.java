/**
 * Classes that represent the lifecyle and extensibility of Sails actions.
 * <p>
 * As a developer, you create controller or component classes that have methods
 * as actions. This is nice, clean, and simple. The rest of the system can
 * access information about you actions through an IAction. These actions can be
 * annotated to provide certain behaviors, some of which are found here. You may
 * also register IActionListeners with the dependency injection container to
 * hear action lifecycle events. It is possible to create new types of
 * IActionResults and IActionResultProcessors to handle them.
 */
package org.opensails.sails.action;
