package org.opensails.dock.adapters;

import org.opensails.dock.model.Person;
import org.opensails.sails.adapter.AdaptationException;
import org.opensails.sails.adapter.IAdapter;

public class PersonAdapter implements IAdapter {
	public Class[] getSupportedTypes() {
		return new Class[] { Person.class };
	}

	public Object forModel(Class modelType, Object fromWeb) throws AdaptationException {
		return new Person();
	}

	public Object forWeb(Class modelType, Object fromModel) throws AdaptationException {
		return ((Person) fromModel).getName();
	}
}
