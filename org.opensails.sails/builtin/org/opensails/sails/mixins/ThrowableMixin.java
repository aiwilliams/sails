package org.opensails.sails.mixins;

import org.opensails.sails.html.StackTrace;

public class ThrowableMixin {

	public StackTrace html(Throwable throwable) {
		return new StackTrace(throwable);
	}
}
