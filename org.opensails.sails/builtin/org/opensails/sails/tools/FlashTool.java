package org.opensails.sails.tools;

import org.opensails.sails.oem.Flash;
import org.opensails.sails.template.IMixinMethod;

/**
 * Exposes the Flash
 */
public class FlashTool implements IMixinMethod {
	protected final Flash flash;

	public FlashTool(Flash flash) {
		this.flash = flash;
	}

	public Object invoke(Object... args) {
		if (args == null || args.length == 0) return "";
		Object value = flash.get(args[0]);
		return value == null ? "" : value;
	}
}
