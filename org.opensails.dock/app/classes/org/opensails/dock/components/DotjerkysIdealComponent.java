package org.opensails.dock.components;

import org.opensails.sails.component.oem.BaseComponent;
import org.opensails.sails.persist.IDataMapper;

public class DotjerkysIdealComponent extends BaseComponent {
	private final IDataMapper persister;

	String requiredProperty;
	// @Remembered
	String anotherProperty = "default";
	Object complex;

	public DotjerkysIdealComponent(IDataMapper mapper) {
		this.persister = mapper;
	}

	public void initialize(String id, String requiredProperty) {
		// create(id);
		this.requiredProperty = requiredProperty;
	}

	// @Callback
	void doSomething(String somethingInteresting) {
		// assert id != null;
		assert anotherProperty != null;
		assert requiredProperty == null;
		assert somethingInteresting.equals("really really interesting");
		persister.commit();
		// renderString("very good");
	}
}
