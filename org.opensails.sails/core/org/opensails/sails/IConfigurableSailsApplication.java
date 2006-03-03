package org.opensails.sails;

import javax.servlet.Servlet;

import org.apache.commons.configuration.Configuration;
import org.opensails.sails.oem.Dispatcher;

public interface IConfigurableSailsApplication extends ISailsApplication, Servlet {
	void setConfiguration(Configuration configuration);

	void setConfigurator(ISailsApplicationConfigurator configurator);

	void setContainer(ApplicationContainer container);

	void setDispatcher(Dispatcher dispatcher);

	void setName(String name);
}
