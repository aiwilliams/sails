package org.opensails.sails.tools;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.oem.FragmentCache;
import org.opensails.sails.oem.FragmentKey;
import org.opensails.viento.Block;

/**
 * A template tool that provides access to parts of the cache.
 * <p>
 * <code><pre>
 *  $cache.fragment(name) [[ ... ]]]
 * </pre></code> You may expire these, in the simplest way, through a controller using
 * expire* methods.
 * 
 * @author aiwilliams
 */
public class CacheTool {
	protected final FragmentCache fragmentCache;
	protected final ISailsEvent event;

	public CacheTool(ISailsEvent event, FragmentCache fragmentCache) {
		this.event = event;
		this.fragmentCache = fragmentCache;
	}

	/**
	 * Cache of block is bound to controller/action.
	 * 
	 * @param block
	 * @return the single fragment for controller/action
	 */
	public String fragment(Block block) {
		return content(new FragmentKey(event), block);
	}

	/**
	 * Cache of block is bound to controller/action/name.
	 * 
	 * @param name
	 * @param block
	 * @return the single fragment for controller/action/name
	 */
	public String fragment(String name, Block block) {
		return content(new FragmentKey(event, name), block);
	}

	protected String content(FragmentKey fragmentKey, Block block) {
		String content = fragmentCache.read(fragmentKey);
		if (content == null) {
			content = block.evaluate();
			fragmentCache.write(fragmentKey, content);
		}
		return content;
	}
}
