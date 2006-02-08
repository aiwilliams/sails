package org.opensails.functional.components;

import org.opensails.sails.component.Callback;
import org.opensails.sails.component.HttpMethod;
import org.opensails.sails.component.Remembered;
import org.opensails.sails.component.oem.BaseComponent;

public class ScriptInitComponent extends BaseComponent {
	public String property;
	@Remembered public Class classProperty;
	public void initialize(String id) {
		super.initialize(id);
	}
	
	@Callback(method = HttpMethod.GET) public void callback() {
	}
	
	@Override
	public String renderThyself() {
		return scriptInit().render();
	}
}
