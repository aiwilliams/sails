package org.opensails.sails.event;

import org.opensails.sails.event.oem.*;

public interface INamespacedProcessorResolver<C extends IActionEventProcessor> extends IActionEventProcessorResolver<C> {
	boolean resolvesNamespace(String namespace);
}
