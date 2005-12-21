package org.opensails.sails.action;

public interface IActionParameterList {
	Object[] objects(Class<?>[] targetTypes);

	int size();
}
