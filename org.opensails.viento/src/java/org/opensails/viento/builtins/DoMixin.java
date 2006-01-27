package org.opensails.viento.builtins;

import org.opensails.viento.Block;
import org.opensails.viento.Name;

public class DoMixin {
	@Name("do")
	public String evaluateAndReturnNothing(Block block) {
		block.evaluate();
		return "";
	}
}
