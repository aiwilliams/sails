package org.opensails.sails.tools;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.oem.FragmentCache;
import org.opensails.viento.Block;

/**
 * A template tool that provides access to parts of the cache.
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
		String content = fragmentCache.read(event.getContextIdentifier(), "");
		if (content == null) {
			content = block.evaluate();
			fragmentCache.write(event.getContextIdentifier(), "", content);
		}
		return content;
	}
}
