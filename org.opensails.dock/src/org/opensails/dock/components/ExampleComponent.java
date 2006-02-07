package org.opensails.dock.components;

import org.opensails.sails.component.Callback;
import org.opensails.sails.component.oem.BaseComponent;
import org.opensails.sails.persist.IObjectPersister;

public class ExampleComponent extends BaseComponent {
	String requiredProperty;
	// @Remembered
	String anotherProperty = "default";
	Object complex;
	IObjectPersister persister;

	public ExampleComponent(IObjectPersister persister) {
		this.persister = persister;
	}

	public void initialize(String id, String requiredProperty) {
		initialize(id);
		this.requiredProperty = requiredProperty;
	}

	@Callback void doSomething(String somethingInteresting) {
		assert id != null;
		assert anotherProperty != null;
		assert requiredProperty == null;
		assert somethingInteresting.equals("really really interesting");
		renderString("very good");
	}
}
