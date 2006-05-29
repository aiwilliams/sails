package org.opensails.sails.oem;

import org.opensails.sails.template.IFragmentStore;
import org.opensails.sails.tools.CacheTool;

/**
 * An application-scoped cache for fragments.
 * <p>
 * A FragmentCache provides the ability to cache expensively generated parts of
 * the output of an action for later use. The {@link CacheTool} provides access
 * to this mechanism in templates.
 * <p>
 * Content can be placed in the FragmentCache and later expired. In order to use
 * the FragmentCache, an IFragmentStore must be provided. A default
 * implementation of MemoryFragmentStore is provided.
 * 
 * @author aiwilliams
 */
public class FragmentCache {
	protected final IFragmentStore store;

	public FragmentCache(IFragmentStore store) {
		this.store = store;
	}

	/**
	 * @param contextIdentifier the context within which the named content
	 *        exists
	 * @return the content or null if not present
	 */
	public String read(String contextIdentifier) {
		return read(contextIdentifier, "");
	}

	/**
	 * @param contextIdentifier the context within which the named content
	 *        exists
	 * @param name the name of the content for the context
	 * @return the content or null if not present
	 */
	public String read(String contextIdentifier, String name) {
		return store.read(contextIdentifier, name);
	}

	/**
	 * @param contextIdentifier the context within which the named content will
	 *        be placed
	 * @param name the name of the content for the context
	 * @param content the content
	 */
	public void write(String contextIdentifier, String content) {
		write(contextIdentifier, "", content);
	}

	/**
	 * @param contextIdentifier the context within which the named content will
	 *        be placed
	 * @param name the name of the content for the context
	 * @param content the content
	 */
	public void write(String contextIdentifier, String name, String content) {
		store.write(contextIdentifier, name, content);
	}

	/**
	 * @param contextIdentifier the context within which the named content
	 *        exists
	 */
	public void expire(String contextIdentifier) {
		expire(contextIdentifier, "");
	}

	/**
	 * @param contextIdentifier the context within which the named content
	 *        exists
	 * @param name the name of the content for the context
	 */
	public void expire(String contextIdentifier, String name) {
		store.delete(contextIdentifier, name);
	}

}
