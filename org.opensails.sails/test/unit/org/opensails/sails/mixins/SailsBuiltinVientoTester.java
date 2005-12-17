package org.opensails.sails.mixins;

import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.template.MixinResolver;
import org.opensails.sails.util.ComponentPackage;
import org.opensails.viento.Binding;
import org.opensails.viento.tester.VientoTester;

public class SailsBuiltinVientoTester extends VientoTester {
	private final ISailsEvent event;

	public SailsBuiltinVientoTester(ISailsEvent event) {
		this.event = event;
	}
	
	@Override
	protected Binding initializeBinding() {
		Binding binding = super.initializeBinding();
		MixinResolver mixinResolver = new MixinResolver(event);
		mixinResolver.push(new ComponentPackage(SailsBuiltinVientoTester.class.getPackage().getName(), "Mixin"));
		binding.mixin(mixinResolver);
		return binding;
	}
}
