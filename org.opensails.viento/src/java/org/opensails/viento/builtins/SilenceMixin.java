package org.opensails.viento.builtins;

import org.opensails.viento.Block;
import org.opensails.viento.ExceptionHandler;
import org.opensails.viento.Name;
import org.opensails.viento.TargetedMethodKey;
import org.opensails.viento.TopLevelMethodKey;

public class SilenceMixin {
	@Name("!")
	public Silence bang(Block block) {
		return new Silence(block);
	}
	
	public class Silence implements ExceptionHandler {
		protected Block block;
		protected Block notFound;
		protected boolean somethingFailed = false;

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
			block.getBinding().setExceptionHandler(this);
			String result = block.evaluate();
			if (!somethingFailed)
				return result;
			if (notFound != null)
				return notFound.evaluate();
			return "";
		}

		private Object somethingFailed() {
			somethingFailed = true;
			return "";
		}

		public Object resolutionFailed(TargetedMethodKey key, Object target, Object[] args, int line, int offset) {
			return somethingFailed();
		}

		public Object resolutionFailed(TopLevelMethodKey key, Object[] args, int line, int offset) {
			return somethingFailed();
		}

		public Object resolutionFailed(Throwable exception, TopLevelMethodKey key, Object[] args, int line, int offset) {
			return somethingFailed();
		}

		public Object resolutionFailed(Throwable exception, TargetedMethodKey key, Object target, Object[] args, int line, int offset) {
			return somethingFailed();
		}
	}
}
