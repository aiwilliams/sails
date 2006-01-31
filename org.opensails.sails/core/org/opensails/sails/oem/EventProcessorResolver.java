package org.opensails.sails.oem;

import java.util.*;

import org.apache.commons.lang.*;
import org.opensails.sails.event.*;
import org.opensails.sails.event.oem.*;

public class EventProcessorResolver implements IActionEventProcessorResolver {
	protected List<INamespacedProcessorResolver> resolvers = new ArrayList<INamespacedProcessorResolver>();

	public void push(INamespacedProcessorResolver resolver) {
		resolvers.add(0, resolver);
	}

	/*
	 * mydomain.com/context/servlet/controller/action/1/2
	 * mydomain.com/context/servlet/component_autocomplete/action/1/2
	 */
	public IActionEventProcessor resolve(String processorIdentifier) {
		String namespace = "Controller";
		String identifier = null;

		String[] namespaceIdentifier = processorIdentifier.split("_");
		if (namespaceIdentifier.length == 2) {
			namespace = StringUtils.capitalize(namespaceIdentifier[0]);
			identifier = namespaceIdentifier[1];
		} else identifier = processorIdentifier;

		for (INamespacedProcessorResolver resolver : resolvers)
			if (resolver.resolvesNamespace(namespace)) return resolver.resolve(identifier);

		return null;
	}
}
