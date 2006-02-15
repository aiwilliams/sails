package org.opensails.sails.adapters;

import org.opensails.sails.adapter.AbstractAdapter;
import org.opensails.sails.adapter.AdaptationException;

public class ClassAdapter extends AbstractAdapter<Class, String> {

	public Class forModel(Class<? extends Class> modelType, String fromWeb) throws AdaptationException {
		try {
			return Class.forName(fromWeb);
		} catch (ClassNotFoundException e) {
			throw new AdaptationException("Could not adapt class " + fromWeb, e);
		}
	}

	public String forWeb(Class<? extends Class> modelType, Class fromModel) throws AdaptationException {
		return fromModel.getName();
	}
}
