package org.opensails.sails.template.viento;

import org.opensails.sails.html.IInlineContent;
import org.opensails.viento.Block;

/**
 * Provides inline content by way of a Viento Block.
 * 
 * @author aiwilliams
 */
public class BlockInlineContent implements IInlineContent {
	private final Block block;

	public BlockInlineContent(Block block) {
		this.block = block;
	}

	public String render() {
		return block.evaluate();
	}
}
