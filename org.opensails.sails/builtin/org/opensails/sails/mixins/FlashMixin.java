package org.opensails.sails.mixins;

import org.opensails.sails.oem.Flash;
import org.opensails.sails.template.IMixinMethod;

/**
 * Exposes the Flash
 */
public class FlashMixin implements IMixinMethod {
	protected final Flash flash;

	public FlashMixin(Flash flash) {
		this.flash = flash;
	}

	public Object invoke(Object... args) {
		if (args == null || args.length == 0) return "";
		Object value = flash.get(args[0]);
		return value == null ? "" : value;
	}
}
