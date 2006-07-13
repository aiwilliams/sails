package org.opensails.js;

import org.opensails.viento.IRenderable;

public interface IJavascriptRenderable extends IRenderable {
	String renderThyself(boolean strictJson);
}
