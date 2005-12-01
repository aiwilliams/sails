package org.opensails.sails;

import javax.servlet.Servlet;

import org.apache.commons.configuration.Configuration;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.oem.Dispatcher;

public interface IConfigurableSailsApplication extends ISailsApplication, Servlet {
    void setConfiguration(Configuration configuration);

    void setContainer(ScopedContainer container);

    void setDispatcher(Dispatcher dispatcher);

    void setName(String name);

	void setConfigurator(ISailsApplicationConfigurator configurator);
}
