package org.opensails.sails.template.viento;

import org.opensails.sails.mixins.HtmlMixin;
import org.opensails.sails.mixins.ThrowableMixin;
import org.opensails.viento.Binding;

public class VientoBinding extends Binding {
	public VientoBinding() {
		super();
	}
	
	public VientoBinding(Binding parent) {
		super(parent);
	}

	public VientoBinding(VientoBinding parent) {
		super(parent);
	}

	@Override
	protected void populateDefaults() {
		super.populateDefaults();
		mixin(Throwable.class, new ThrowableMixin());
		mixin(String.class, new HtmlMixin());
	}
}
