package org.opensails.sails.template;

import org.opensails.sails.template.viento.VientoBinding;
import org.opensails.viento.IBinding;

public class TemplateBindingFixture {
	public static IBinding create() {
		return new VientoBinding();
	}
}
