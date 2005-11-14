package org.opensails.sails.tester;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.Sails;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.tester.form.TestFormFields;
import org.opensails.sails.tester.oem.TestingHttpServletResponse;
import org.opensails.sails.tester.persist.IShamObjectPersister;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpSession;
import org.opensails.sails.tester.servletapi.ShamServletConfig;
import org.opensails.sails.tester.servletapi.ShamServletContext;
import org.opensails.sails.util.ClassInstanceAccessor;

/**
 * Think of this as being a browser. Through it you request pages, and it
 * communicates with an ISailsApplication to render responses.
 */
public class SailsTester implements ISailsApplication {
	protected TestableSailsApplication application;
	// Container used when events are generated
	protected TestRequestContainer requestContainer;
	protected ShamHttpSession session;
	protected Class<? extends IControllerImpl> workingController;

	public SailsTester() {
		initialize(BaseConfigurator.class);
	}

	public SailsTester(Class<? extends BaseConfigurator> configurator) {
		initialize(configurator);
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @return the default page of the workingController, if set, otherwise the
	 *         default controller (Home, though that is left up to the
	 *         application).
	 */
	public Page get() {
		return get(getWorkingControllerPathInfoForRequest());
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @param controller the controller, which becomes the working controller
	 * @return the default page for the given controller
	 */
	public Page get(Class<? extends IControllerImpl> controller) {
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
	public Page get(Class<? extends IControllerImpl> controller, String action, String... parameters) {
		this.workingController = controller;
		return get(action, parameters);
	}

	public Page get(String controllerAction) {
		return application.get(createGetEvent(controllerAction));
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
	 * controller. The other get methods, which take an IControllerImpl class,
	 * are what should be used unless there is no controller class for the
	 * action you would like to get.
	 * 
	 * @param controller the controller name
	 * @param action
	 * @param parameters
	 * @return the page for the given controller/action
	 */
	public Page get(String controller, String action, String... parameters) {
		TestGetEvent event = createGetEvent(controller, action);
		event.setActionParameters(parameters);
		event.getContainer().registerAll(getRequestContainer());
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

	/**
	 * You should get a new one of these for each request you expect to post.
	 * 
	 * @return a new instance of TestFormFields
	 */
	public TestFormFields getFormFields() {
		throw new RuntimeException("This need to return one created from REQUEST container");
		// return new
		// TestFormFields(getContainer().instance(ContainerAdapterResolver.class));
	}

	public String getName() {
		return application.getName();
	}

	public TestRequestContainer getRequestContainer() {
		return requestContainer;
	}

	public TestSession getSession() {
		return new TestSession(this);
	}

	/**
	 * @return the current session. If create, creates one. If not, returns
	 *         null.
	 */
	public ShamHttpSession getSession(boolean create) {
		return (session == null && create) ? session = new ShamHttpSession() : session;
	}

	/**
	 * TODO register at the requested scope
	 */
	public <T> void inject(Class<? extends T> key, Class<? extends T> implementation, ApplicationScope scope) {
		getRequestContainer().inject(key, implementation);
	}

	/**
	 * TODO register at the requested scope
	 */
	public <T> void inject(Class<? super T> key, T instance, ApplicationScope scope) {
		getRequestContainer().inject(key, instance);
	}

	/**
	 * Gets rid of the current HttpSession. Any references to the old
	 * HttpSession are not 'managed' - that is, new requests will have a
	 * different session.
	 */
	public void newSession() {
		session = null;
	}

	public Page post(Class<? extends IControllerImpl> controller, FormFields formFields) {
		return post(Sails.controllerName(controller), formFields);
	}

	public Page post(Class<? extends IControllerImpl> controller, String action, FormFields formFields, IIdentifiable... actionParameters) {
		TestPostEvent event = createPostEvent(Sails.controllerName(controller), action, formFields);
		RequestContainer container = event.getContainer();
		if (actionParameters != null && actionParameters.length > 0) {
			IShamObjectPersister persister = container.instance(IShamObjectPersister.class);
			IAdapterResolver resolver = container.instance(IAdapterResolver.class);
			String[] params = new String[actionParameters.length];
			for (int i = 0; i < actionParameters.length; i++) {
				IIdentifiable object = actionParameters[i];
				persister.provides(object);
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
		return post(getWorkingControllerPathInfoForRequest(), formFields);
	}

	public Page post(String controllerAction, FormFields formFields) {
		return application.post(createPostEvent(controllerAction, formFields));
	}

	public void setWorkingController(Class<? extends IControllerImpl> controller) {
		this.workingController = controller;
	}

	protected TestGetEvent createGetEvent(String pathInfo) {
		ShamHttpServletRequest request = createRequest();
		request.setPathInfo(pathInfo);
		TestingHttpServletResponse response = new TestingHttpServletResponse();
		TestGetEvent event = new TestGetEvent(this, requestContainer, request, response);
		requestContainer.bind(event);
		response.set(event);
		return event;

	}

	protected TestGetEvent createGetEvent(String controller, String action) {
		return createGetEvent(controller + "/" + action);
	}

	protected TestPostEvent createPostEvent(String pathInfo, FormFields formFields) {
		ShamHttpServletRequest request = createRequest();
		request.setPathInfo(pathInfo);
		request.setParameters(formFields.toMap());
		TestingHttpServletResponse response = new TestingHttpServletResponse();
		TestPostEvent event = new TestPostEvent(this, requestContainer, request, response);
		requestContainer.bind(event);
		response.set(event);
		return event;
	}

	protected TestPostEvent createPostEvent(String controller, String action, FormFields formFields) {
		return createPostEvent(controller + "/" + action, formFields);
	}

	/**
	 * @return a request that is bound to this application such that when a
	 *         session is created, we have it
	 */
	protected ShamHttpServletRequest createRequest() {
		return new ShamHttpServletRequest(session) {
			@Override
			public javax.servlet.http.HttpSession getSession() {
				return session = (ShamHttpSession) super.getSession();
			};

			@Override
			public javax.servlet.http.HttpSession getSession(boolean create) {
				return session = (ShamHttpSession) super.getSession(create);
			};
		};
	}

	protected String getWorkingControllerPathInfoForRequest() {
		if (workingController != null) return Sails.controllerName(workingController);
		else return "";
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
		// TODO: Bind to lazy created session container
		requestContainer = new TestRequestContainer(getContainer());
	}
}
