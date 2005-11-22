package org.opensails.viento.builtins;

import org.opensails.viento.IBinding;


public class SetMixin {
	protected IBinding binding;

	public SetMixin(IBinding binding) {
		this.binding = binding;
	}
	
	public void set(String key, Object value) {
		binding.put(key, value);
	}
}
