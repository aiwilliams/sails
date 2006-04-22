package org.opensails.sails.tester.oem;

import org.opensails.viento.Binding;

public class TestingBinding extends Binding {
	public Object get(String key) {
		return statics.get(key);
	}
}
