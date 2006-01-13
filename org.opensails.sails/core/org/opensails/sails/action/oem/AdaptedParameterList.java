package org.opensails.sails.action.oem;

import java.util.ArrayList;
import java.util.List;

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

	public String stringAt(int index) throws IndexOutOfBoundsException {
		return strings()[index];
	}

	public String[] strings() {
		List<String> strings = new ArrayList<String>(parameters.length);
		for (int i = 0; i < parameters.length; i++)
			strings.add(String.valueOf(parameters[i]));
		return strings.toArray(new String[strings.size()]);
	}
}
