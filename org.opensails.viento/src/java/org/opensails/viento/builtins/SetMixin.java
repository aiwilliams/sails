package org.opensails.viento.builtins;

import org.opensails.viento.Binding;


public class SetMixin {
	protected Binding binding;

	public SetMixin(Binding binding) {
		this.binding = binding;
	}
	
	public void set(String key, Object value) {
		binding.put(key, value);
	}
}
