package org.opensails.viento.builtins;

import org.opensails.viento.Block;

public class WithMixin {
	public Block with(Object object, Block block) {
		block.getBinding().mixin(object);
		return block;
	}
}
