package org.opensails.sails;

/**
 * Used to define the ApplicationScope of Sails components.
 * 
 * Various pieces of a Sails application can exist at different lifecycle
 * scopes. Likely the best example of this would be
 * {@link org.opensails.sails.adapter.IAdapter} instances.
 * 
 * @see org.opensails.sails.Scope
 */
public enum ApplicationScope {
	REQUEST, SERVLET, SERVLET_CONTEXT, SESSION, VIRTUAL_MACHINE
}
