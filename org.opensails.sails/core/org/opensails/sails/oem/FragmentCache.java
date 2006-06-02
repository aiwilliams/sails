package org.opensails.sails.oem;

import java.util.HashSet;
import java.util.Set;

import org.opensails.sails.template.IFragmentStore;
import org.opensails.sails.tools.CacheTool;
import org.opensails.sails.util.RegexHelper;

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
	 * @param identifer the context within which the named content exists
	 */
	public void expire(FragmentKey identifer) {
		store.delete(identifer);
	}

	/**
	 * @param pattern
	 * @return all keys matching pattern
	 */
	public Set<FragmentKey> keys(String pattern) {
		Set<FragmentKey> keys = new HashSet<FragmentKey>();
		for (FragmentKey key : store.keySet()) {
			if (RegexHelper.containsMatch(key.toString(), pattern)) keys.add(key);
		}
		return keys;
	}

	/**
	 * @param identifer the context within which the named content exists
	 * @return the content or null if not present
	 */
	public String read(FragmentKey identifer) {
		return store.read(identifer);
	}

	/**
	 * @param identifer the context within which the named content will be
	 *        placed
	 * @param name the name of the content for the context
	 * @param content the content
	 */
	public void write(FragmentKey identifer, String content) {
		store.write(identifer, content);
	}
}
