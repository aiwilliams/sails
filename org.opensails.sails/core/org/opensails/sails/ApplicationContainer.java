package org.opensails.sails;

import org.opensails.rigging.ScopedContainer;

public class ApplicationContainer extends ScopedContainer {
	public ApplicationContainer() {
		super(ApplicationScope.SERVLET);
	}
}
