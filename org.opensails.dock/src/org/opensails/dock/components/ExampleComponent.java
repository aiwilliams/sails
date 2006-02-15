package org.opensails.dock.components;

import org.opensails.sails.component.Callback;
import org.opensails.sails.component.Remembered;
import org.opensails.sails.component.oem.BaseComponent;

public class ExampleComponent extends BaseComponent {
	public String requiredProperty;
	@Remembered String rememberedProperty = "default";
	Object complex;

	// TODO: Remove public once callbacks are restricted
	@Callback public void doSomething(String actionParamProvidedToClientSideCallback) {
		renderString("Remembered: " + rememberedProperty + " Action Param: " + actionParamProvidedToClientSideCallback);
	}

	public void initialize(String id, String requiredProperty) {
		initialize(id);
		this.requiredProperty = requiredProperty;
	}
}
