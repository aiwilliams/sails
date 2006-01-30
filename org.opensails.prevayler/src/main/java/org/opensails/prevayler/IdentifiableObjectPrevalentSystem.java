package org.opensails.prevayler;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.util.DoubleKeyedMap;

public class IdentifiableObjectPrevalentSystem implements Serializable {
	DoubleKeyedMap<Class, Long, IIdentifiable> objects = new DoubleKeyedMap<Class, Long, IIdentifiable>();

	public void remove(IIdentifiable object) {
		objects.remove(object.getClass(), object.getId());
	}

	public Object get(Class objectType, Long id) {
		return objects.get(objectType, id);
	}

	public Collection getAll(Class objectType) {
		HashMap objectTypes = (HashMap) objects.get(objectType);
		return objectTypes == null ? Collections.EMPTY_LIST : objectTypes.values();
	}

	public void save(IIdentifiable object) {
		objects.put(object.getClass(), object.getId(), object);
	}
}
