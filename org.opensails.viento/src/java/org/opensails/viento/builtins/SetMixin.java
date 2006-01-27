package org.opensails.viento.builtins;

import org.opensails.viento.Binding;
import org.opensails.viento.Name;


public class SetMixin {
	protected Binding binding;

	public SetMixin(Binding binding) {
		this.binding = binding;
	}
	
	public void set(String key, Object value) {
		binding.put(key, value);
	}
	
	@Name("default")
	public void setIfNotAlreadyThere(String key, Object value) {
		if (!binding.canResolve(key))
			binding.put(key, value);
	}
}
