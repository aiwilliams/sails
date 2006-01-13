package org.opensails.sails.action.oem;

import org.opensails.sails.action.IActionParameterList;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;

/*
 * @author Adam 'Programmer' Williams
 */
public class ActionParameterList implements IActionParameterList {
	private Object[] adaptedParameters;
	private final ContainerAdapterResolver adapterResolver;
	private final String[] urlParameters;

	public ActionParameterList(String[] urlParameters, ContainerAdapterResolver adapterResolver) {
		this.urlParameters = urlParameters;
		this.adapterResolver = adapterResolver;
	}

	@SuppressWarnings("unchecked")
	public Object[] objects(Class<?>[] targetTypes) {
		if (adaptedParameters == null) {
			adaptedParameters = new Object[targetTypes.length];
			for (int i = 0; i < targetTypes.length; i++) {
				IAdapter adapter = adapterResolver.resolve(targetTypes[i]);
				Object adapted = adapter.forModel(targetTypes[i], urlParameters[i]);
				adaptedParameters[i] = adapted;
			}
		}
		return adaptedParameters;
	}

	public int size() {
		return urlParameters.length;
	}

	public String stringAt(int index) throws IndexOutOfBoundsException {
		return urlParameters[index];
	}

	public String[] strings() {
		return urlParameters;
	}
}
