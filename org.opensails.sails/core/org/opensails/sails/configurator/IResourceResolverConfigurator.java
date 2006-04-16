package org.opensails.sails.configurator;

import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.oem.ResourceResolver;

public interface IResourceResolverConfigurator {

	void configure(IConfigurableSailsApplication application, ResourceResolver resolver);

}
