package org.opensails.viento.builtins;

import org.opensails.viento.Block;
import org.opensails.viento.DefaultExceptionHandler;
import org.opensails.viento.Name;
import org.opensails.viento.ResolutionFailedException;

public class SilenceMixin {
	@Name("!")
	public Silence bang(Block block) {
		return new Silence(block);
	}
	
	public class Silence {
		protected Block block;
		protected Block notFound;

		public Silence(Block block) {
			this.block = block;
		}
		
		@Name("?")
		public Silence notFound(Block block) {
			this.notFound = block;
			return this;
		}
		
		@Override
		public String toString() {
			block.getBinding().setExceptionHandler(new DefaultExceptionHandler());
			try {
				return block.evaluate();
			} catch (ResolutionFailedException e) {
				if (notFound != null)
					return notFound.evaluate();
				return "";
			}
		}
	}
}
