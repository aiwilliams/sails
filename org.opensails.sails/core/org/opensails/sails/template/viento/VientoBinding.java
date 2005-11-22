package org.opensails.sails.template.viento;

import org.opensails.sails.mixins.ThrowableMixin;
import org.opensails.viento.Binding;
import org.opensails.viento.ExceptionHandler;

public class VientoBinding extends Binding {
	public VientoBinding() {
		super();
	}

	public VientoBinding(Binding parent) {
		super(parent);
	}

	public VientoBinding(ExceptionHandler exceptionHandler) {
		setExceptionHandler(exceptionHandler);
	}

	@Override
	protected void populateDefaults() {
		super.populateDefaults();
		mixin(Throwable.class, new ThrowableMixin());
	}
}
