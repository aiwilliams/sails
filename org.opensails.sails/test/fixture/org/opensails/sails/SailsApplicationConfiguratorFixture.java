package org.opensails.sails;

import javax.servlet.ServletConfig;

import org.opensails.sails.configurator.SailsConfigurator;
import org.opensails.sails.tester.TestApplicationConfigurator;
import org.opensails.sails.tester.servletapi.ShamServletConfig;
import org.opensails.spyglass.SpyObject;

public class SailsApplicationConfiguratorFixture {
	/**
	 * Configure a SailsApplication for testing using an instance of
	 * {@link TestApplicationConfigurator}
	 * 
	 * @param application
	 */
	public static void configure(IConfigurableSailsApplication application) {
		configure(application, new TestApplicationConfigurator(SailsConfigurator.class));
	}

	/**
	 * Configure a SailsApplication for testing, allowing the use of a specific
	 * {@link ISailsApplicationConfigurator} instance.
	 * 
	 * @param application
	 */
	public static void configure(IConfigurableSailsApplication application, ISailsApplicationConfigurator configurator) {
		configure(application, configurator, new ShamServletConfig());
	}

	/**
	 * Configure a SailsApplication for testing, allowing the use of a specific
	 * {@link ISailsApplicationConfigurator} instance and ServletConfig.
	 * 
	 * @param application
	 */
	public static void configure(IConfigurableSailsApplication application, ISailsApplicationConfigurator configurator, ServletConfig config) {
		// config is on GenericServlet
		SpyObject<IConfigurableSailsApplication> applicationSpy = SpyObject.create(application);
		applicationSpy.write("config", config);
		configurator.configure(application);
		applicationSpy.invoke("startApplication");
	}

}
