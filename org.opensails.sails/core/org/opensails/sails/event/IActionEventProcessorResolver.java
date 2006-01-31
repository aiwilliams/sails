package org.opensails.sails.event;

import org.opensails.sails.event.oem.*;

public interface IActionEventProcessorResolver<C extends IActionEventProcessor> {
	C resolve(String processorIdentifier);
}
