package org.opensails.sails.tester.oem;

import org.opensails.sails.template.viento.VientoBinding;

public class TestingBinding extends VientoBinding {
	public Object get(String key) {
		return statics.get(key);
	}
}
