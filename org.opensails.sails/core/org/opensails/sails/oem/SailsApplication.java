package org.opensails.sails.oem;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.ClassUtils;
import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.IConfigurableSailsApplication;
import org.opensails.sails.ISailsApplicationConfigurator;
import org.opensails.sails.SailsException;
import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.PostEvent;

public class SailsApplication extends HttpServlet implements IConfigurableSailsApplication {
	private static final long serialVersionUID = 1704190879499722509L;

	protected ApplicationContainer applicationContainer;
	protected Configuration configuration;
	protected ISailsApplicationConfigurator configurator;
	protected Dispatcher dispatcher;
	protected String name;

	@Override
	public void destroy() {
		applicationContainer.stop();
		applicationContainer.dispose();
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public ApplicationContainer getContainer() {
		return applicationContainer;
	}

	public String getName() {
		return name;
	}

	@Override
	public void init() throws ServletException {
		configureAndStart(getServletConfig());
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}

	public void setConfigurator(ISailsApplicationConfigurator configurator) {
		this.configurator = configurator;
	}

	public void setContainer(ApplicationContainer container) {
		applicationContainer = container;
	}

	public void setDispatcher(Dispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected void configureAndStart(Class configuratorClass) throws InstantiationException, IllegalAccessException {
		// TODO: Logging
		// LOG.info("Using SailsConfigurator class <" +
		// configuratorClassName + "> as defined in the web.xml by the
		// parameter <" + configuratorParamName + ">");
		ISailsApplicationConfigurator configurator = (ISailsApplicationConfigurator) configuratorClass.newInstance();
		configureAndStart(configurator);
	}

	protected void configureAndStart(ISailsApplicationConfigurator configurator) {
		this.configurator = configurator;
		this.configurator.configure(this);
		if (configuration == null) throw new IllegalStateException("Your implementation of " + ISailsApplicationConfigurator.class
				+ " must install an application configuration that implements " + Configuration.class);
		if (applicationContainer == null) throw new IllegalStateException("Your implementation of " + ISailsApplicationConfigurator.class
				+ " must install an ApplicationContainer");
		if (dispatcher == null) throw new IllegalStateException("Your implementation of " + ISailsApplicationConfigurator.class + " must install a " + Dispatcher.class);
		this.startApplication();
	}

	protected void configureAndStart(ServletConfig config) {
		String configuratorParamName = ISailsApplicationConfigurator.class.getName();
		String configuratorClassName = getConfiguratorClassName(config, configuratorParamName);
		String configuratorInterface = ClassUtils.getShortClassName(ISailsApplicationConfigurator.class);
		if (configuratorClassName == null) throw new IllegalStateException("You must provide the init-param named '" + configuratorParamName
				+ "' that contains a value that is the fully-qualified class name of the " + configuratorInterface + " to configure your application with.");
		try {
			Class configuratorClass = Class.forName(configuratorClassName);
			configureAndStart(configuratorClass);
		} catch (ClassNotFoundException e) {
			throw new SailsException("Could not find the " + configuratorInterface + " named " + configuratorClassName + " as defined by the configuration property "
					+ configuratorParamName, e);
		} catch (InstantiationException e) {
			throw new SailsException("Could not instantiate the " + configuratorInterface + " named " + configuratorClassName + " as defined by the configuration property "
					+ configuratorParamName, e);
		} catch (IllegalAccessException e) {
			throw new SailsException("Could not access the " + configuratorInterface + " named " + configuratorClassName + " as defined by the configuration property "
					+ configuratorParamName, e);
		} catch (ClassCastException e) {
			throw new SailsException("The " + configuratorInterface + " named " + configuratorClassName + " as defined by the configuration property " + configuratorParamName, e);
		}
	}

	protected GetEvent createGetEvent(HttpServletRequest req, HttpServletResponse resp) {
		return new GetEvent(this, req, resp);
	}

	protected PostEvent createPostEvent(HttpServletRequest req, HttpServletResponse resp) {
		return new PostEvent(this, req, resp);
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dispatcher.dispatch(createGetEvent(req, resp));
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dispatcher.dispatch(createPostEvent(req, resp));
	}

	protected String getConfiguratorClassName(ServletConfig config, String configuratorParamName) {
		String className = System.getProperty(configuratorParamName);
		if (className != null) return className;
		return config.getInitParameter(configuratorParamName);
	}

	/**
	 * Called after the application has been configured
	 */
	protected void startApplication() {
		applicationContainer.start();
	}
}
