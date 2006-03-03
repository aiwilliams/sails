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
	/**
	 * The Sails 'application' scope.
	 * <p>
	 * Sails is currently implemented as a Servlet. Every request to a Sails
	 * application is processed by a SailsApplication servlet. This scope is
	 * that of a particular SailsApplication instance. Theoretically, one could
	 * have multiple SailsApplication instances within one servlet context.
	 */
	SERVLET,

	/**
	 * The Sails 'event' scope.
	 * <p>
	 * When a request comes in, it gets wrapped in an ISailsEvent. That event
	 * has a Container. This identifies that scope.
	 */
	REQUEST,
	
	COMPONENT,
}
