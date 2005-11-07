package org.opensails.viento.builtins;

import org.opensails.viento.Block;
import org.opensails.viento.Name;

public class IfMixin {
	@Name("if")
	public If create(Object condition, Block block) {
		return new If().condition(condition, block);
	}
	
	public class If {
		Block blockToRender;
		
		@Override
		public String toString() {
			return blockToRender == null ? "" : blockToRender.evaluate();
		}

		@Name("elseif")
		public If condition(Object condition, Block block) {
			if (condition != null && !condition.equals(Boolean.FALSE) && blockToRender == null)
				blockToRender = block;
			return this;
		}
		
		@Name("else")
		public If noCondition(Block block) {
			if (blockToRender == null)
				blockToRender = block;
			return this;
		}
	}
}
