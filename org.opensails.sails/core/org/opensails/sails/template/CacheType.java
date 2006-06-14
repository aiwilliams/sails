package org.opensails.sails.template;

/**
 * @author aiwilliams
 */
public enum CacheType {
	/**
	 * A type of cache where the filters of the action are still executed and
	 * the result of the event can be manipulated. If a filter invalidates the
	 * cache for the action, the action will be executed normally.
	 */
	ACTION,

	/**
	 * TODO: Implement support
	 * 
	 * A type of cache where the entire output of an action will be written to a
	 * file that can be served by the HTTP server. This prevents the event being
	 * dispatched to filters, actions, etc. This is only useful in cases where
	 * the result of an action is the same for all users.
	 */
	PAGE;
}
