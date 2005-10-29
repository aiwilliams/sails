package org.opensails.sails.template;

import org.opensails.sails.template.viento.VientoBinding;

public class TemplateBindingFixture {
	public static ITemplateBinding create() {
		return new VientoBinding();
	}
}
