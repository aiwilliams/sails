package org.opensails.sails.action.oem;

import org.opensails.sails.action.IActionParameterList;

public class AdaptedParameterList implements IActionParameterList {
	private final Object[] parameters;

	public AdaptedParameterList(Object... urlParameters) {
		this.parameters = urlParameters;
	}

	public Object[] objects(Class<?>[] targetTypes) {
		return parameters;
	}

	public int size() {
		return parameters.length;
	}
}
