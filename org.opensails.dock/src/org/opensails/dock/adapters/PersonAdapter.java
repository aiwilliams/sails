package org.opensails.dock.adapters;

import org.opensails.dock.model.Person;
import org.opensails.sails.adapter.AbstractAdapter;
import org.opensails.sails.adapter.AdaptationException;

public class PersonAdapter extends AbstractAdapter<Person, String> {
	public Person forModel(Class<? extends Person> modelType, String fromWeb) throws AdaptationException {
		return new Person();
	}

	public String forWeb(Class<? extends Person> modelType, Person fromModel) throws AdaptationException {
		return fromModel.getName();
	}
}
