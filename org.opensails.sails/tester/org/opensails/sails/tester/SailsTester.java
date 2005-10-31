package org.opensails.sails.tester;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.Sails;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IController;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.servletapi.ShamServletConfig;
import org.opensails.sails.servletapi.ShamServletContext;
import org.opensails.sails.util.ClassInstanceAccessor;

public class SailsTester implements ISailsApplication {
	protected TestableSailsApplication application;
	protected Class<? extends IController> workingController;

	public SailsTester() {
		initialize(BaseConfigurator.class);
	}

	public SailsTester(Class<? extends BaseConfigurator> configurator) {
		initialize(configurator);
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @return the default page of the application, typically home/index. that
	 *         is left up to the application, not determined by this method.
	 */
	public Page get() {
		String controller = "";
		if (workingController != null) controller = Sails.controllerName(workingController);
		return application.get(controller);
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @param controller the controller, which becomes the working controller
	 * @return the default page for the given controller
	 */
	public Page get(Class<? extends IController> controller) {
		this.workingController = controller;
		return get();
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @param controller the controller, which becomes the working controller
	 * @param action
	 * @param parameters
	 * @return the page for the given controller/action
	 */
	public Page get(Class<? extends IController> controller, String action, String... parameters) {
		this.workingController = controller;
		return get(action, parameters);
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @param action on current working controller
	 * @param parameters
	 * @return the page for &lt;workingController&gt;/action
	 */
	public Page get(String action, String... parameters) {
		return get(Sails.controllerName(workingController), action, parameters);
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * This is the 'fundamental' get method. It will not alter the working
	 * controller. The other get methods, which take an IController class, are
	 * what should be used unless there is no controller class for the action
	 * you would like to get.
	 * 
	 * @param controller the controller name
	 * @param action
	 * @param parameters
	 * @return the page for the given controller/action
	 */
	public Page get(String controller, String action, String... parameters) {
		TestGetEvent event = application.createGetEvent(controller, action);
		event.setActionParameters(parameters);
		return application.get(event);
	}

	public Configuration getConfiguration() {
		return application.getConfiguration();
	}

	/**
	 * @return the application container
	 */
	public ScopedContainer getContainer() {
		return application.getContainer();
	}

	public String getName() {
		return application.getName();
	}

	public <T> void inject(Class<? extends T> key, Class<? extends T> implementation) {
        getContainer().register(key, implementation);
    }

	public <T> void inject(Class<? super T> key, T instance) {
    	getContainer().register(key, instance);
    }

	public Page post(Class<? extends IController> controller, String action, FormFields formFields, Object... actionParameters) {
		TestPostEvent event = application.createPostEvent(Sails.controllerName(controller), action, formFields);
		ScopedContainer container = event.getContainer();
		if (actionParameters != null && actionParameters.length > 0) {
			
			IAdapterResolver resolver = container.instance(IAdapterResolver.class);
			String[] params = new String[actionParameters.length];
			for (int i = 0; i < actionParameters.length; i++) {
				Object object = actionParameters[i];
				IAdapter adapter = resolver.resolve(object.getClass(), container);
				params[i] = (String) adapter.forWeb(object.getClass(), object);
			}
			event.setActionParameters(params);
		}
		return application.post(event);
	}

	/**
	 * Performs an HTTP POST request
	 * 
	 * @return the default page of the application, typically home/index. that
	 *         is left up to the application, not determined by this method.
	 */
	public Page post(FormFields formFields) {
		String controller = "";
		if (workingController != null) controller = Sails.controllerName(workingController);
		return application.post(controller, formFields);
	}

	public void setWorkingController(Class<? extends IController> controller) {
		this.workingController = controller;
	}

	protected void initialize(Class<? extends BaseConfigurator> configuratorClass) {
		initialize(configuratorClass, new File(Sails.DEFAULT_CONTEXT_ROOT_DIRECTORY));
	}

    protected void initialize(Class<? extends BaseConfigurator> configuratorClass, File contextRootDirectory) {
		initialize(configuratorClass, new ShamServletConfig(new ShamServletContext(contextRootDirectory)));
	}

    protected void initialize(Class<? extends BaseConfigurator> configuratorClass, ShamServletConfig config) {
		this.application = new TestableSailsApplication();
		new ClassInstanceAccessor(TestableSailsApplication.class, true).setProperty(application, "config", config);
		this.application.configure(new SailsTesterConfigurator(configuratorClass));
	}
}
