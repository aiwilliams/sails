package org.opensails.dock.components;

import org.opensails.sails.component.oem.BaseComponent;

public class ExampleComponent extends BaseComponent {
	String requiredProperty;
	// @Remembered
	String anotherProperty = "default";
	Object complex;

	public ExampleComponent(/* IObjectPersister persister */) {
		// this.persister = persister;
	}

	public void initialize() {
		// create(id);
		this.requiredProperty = requiredProperty;
	}

	// @Callback
	void doSomething(String somethingInteresting) {
		// assert id != null;
		assert anotherProperty != null;
		assert requiredProperty == null;
		assert somethingInteresting.equals("really really interesting");
		// renderString("very good");
	}
}
