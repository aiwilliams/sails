package org.opensails.sails.tester;

import java.io.File;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.lang.ArrayUtils;
import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.ISailsApplication;
import org.opensails.sails.Sails;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.tester.form.TestFormFields;
import org.opensails.sails.tester.oem.TestingHttpServletResponse;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpSession;
import org.opensails.sails.tester.servletapi.ShamServletConfig;
import org.opensails.sails.tester.servletapi.ShamServletContext;
import org.opensails.sails.util.ClassInstanceAccessor;

/**
 * Think of this as being a browser. Through it you request pages, and it
 * communicates with an ISailsApplication to render responses.
 * 
 * <pre>
 *   The main methods to understand browser simulation 
 *   #get(String, String, String[])
 *   #post(Class, FormFields, Object[])
 *   #follow(TestRedirectUrl)
 * </pre>
 */
public class SailsTester implements ISailsApplication {
	protected TestableSailsApplication application;
	// Container used when events are generated
	protected TestRequestContainer requestContainer;
	protected ShamHttpSession session;
	protected Class<? extends IEventProcessingContext> workingContext;

	protected SailsTester() {
	// allow subclass control
	}

	/**
	 * @see org.opensails.sails.ISailsApplicationConfigurator
	 * @param configurator the BaseConfigurator used to configure the
	 *        Application Under Test. Note: if you don't provide a subclass
	 *        BaseConfigurator, the tester will not find the assets of your
	 *        project - it will only be able to find those built into Sails.
	 */
	public SailsTester(Class<? extends BaseConfigurator> configurator) {
		initialize(configurator);
	}

	/**
	 * @param context
	 * @param action
	 * @param parameters
	 * @return
	 */
	private String toPathInfo(String context, String action, String... parameters) {
		StringBuilder pathInfo = new StringBuilder();
		pathInfo.append(context);
		pathInfo.append("/");
		pathInfo.append(action);
		pathInfo.append(toParametersString(parameters));
		return pathInfo.toString();
	}

	@SuppressWarnings("unchecked")
	protected String[] adaptParameters(Object[] parameters, ContainerAdapterResolver resolver) {
		if (parameters != null && parameters.length > 0) {
			String[] params = new String[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				Object object = parameters[i];
				IAdapter adapter = resolver.resolve(object.getClass());
				params[i] = String.valueOf(adapter.forWeb(object.getClass(), object));
			}
			return params;
		}
		return ArrayUtils.EMPTY_STRING_ARRAY;
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

	protected TestGetEvent createGetEvent(String context, String action, String... parameters) {
		return createGetEvent(toPathInfo(context, action, parameters));
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

	protected TestPostEvent createPostEvent(String context, String action, FormFields formFields, String... parameters) {
		return createPostEvent(toPathInfo(context, action, parameters), formFields);
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

	protected void initialize(Class<? extends BaseConfigurator> configuratorClass) {
		initialize(configuratorClass, new File(Sails.DEFAULT_CONTEXT_ROOT_DIRECTORY));
	}

	protected void initialize(Class<? extends BaseConfigurator> configuratorClass, File contextRootDirectory) {
		initialize(configuratorClass, new ShamServletConfig(new ShamServletContext(contextRootDirectory)));
	}

	protected void initialize(Class<? extends BaseConfigurator> configuratorClass, ShamServletConfig config) {
		this.application = new TestableSailsApplication();
		new ClassInstanceAccessor(TestableSailsApplication.class, true).setProperty(application, "config", config);
		this.application.configure(instrumentedConfigurator(configuratorClass));
		prepareForNextRequest();
	}

	protected SailsTesterConfigurator instrumentedConfigurator(Class<? extends BaseConfigurator> configuratorClass) {
		return new SailsTesterConfigurator(configuratorClass);
	}

	protected void prepareForNextRequest() {
		// TODO: Bind to lazy created session container
		if (requestContainer != null) requestContainer = new TestRequestContainer(getContainer(), requestContainer.injections);
		else requestContainer = new TestRequestContainer(getContainer());
	}

	protected String toParametersString(String... parameters) {
		StringBuilder string = new StringBuilder();
		for (String param : parameters) {
			string.append("/");
			string.append(param);
		}
		return string.toString();
	}

	public Page doGet(TestGetEvent getEvent) {
		Page page = application.get(getEvent);
		prepareForNextRequest();
		return page;
	}

	public Page doPost(TestPostEvent postEvent) {
		Page page = application.post(postEvent);
		prepareForNextRequest();
		return page;
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @return the default page of the workingController, if set, otherwise the
	 *         default controller (Home, though that is left up to the
	 *         application).
	 */
	public Page get() {
		return get("index");
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @param context becomes the working context
	 * @return the default page for the given controller
	 */
	public Page get(Class<? extends IEventProcessingContext> context) {
		this.workingContext = context;
		return get();
	}

	/**
	 * Performs an HTTP GET request
	 * 
	 * @param context becomes the working context
	 * @param action
	 * @param parameters
	 * @return the page for the given context/action
	 */
	public Page get(Class<? extends IEventProcessingContext> context, String action, String... parameters) {
		this.workingContext = context;
		return get(action, parameters);
	}

	public Page get(String action) {
		return get(workingContext(), action, ArrayUtils.EMPTY_STRING_ARRAY);
	}

	/**
	 * Performs an HTTP GET request
	 * <p>
	 * Sails supports a form of namespacing for IEventProcessingContext. If no
	 * namespace is declared, the controller namespace is assumed.
	 * 
	 * @see Sails#eventContextName(Class)
	 * @param action on current working context
	 * @param parameters
	 * @return the page for &lt;workingContext&gt;/action
	 */
	public Page get(String action, String... parameters) {
		return get(workingContext(), action, parameters);
	}

	/**
	 * Performs an HTTP GET request
	 * <p>
	 * This is the 'fundamental' get method. It will not alter the working
	 * context. The other get methods, which take an
	 * {@link IEventProcessingContext} class, are what should be used unless
	 * there is no context class for the action you would like to get.
	 * 
	 * @param context the context identifier
	 * @param action
	 * @param parameters
	 * @return the page for the given context/action
	 */
	public Page get(String context, String action, String... parameters) {
		TestGetEvent event = createGetEvent(context, action, parameters);
		return get(event);
	}

	protected Page get(TestGetEvent event) {
		event.getContainer().registerAll(getRequestContainer());
		return doGet(event);
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
	public <T> void inject(Class<? super T> key, Class<T> implementation, ApplicationScope scope) {
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

	public Page post(Class<? extends IEventProcessingContext> context, FormFields formFields, Object... parameters) {
		return post(Sails.eventContextName(context), formFields, parameters);
	}

	public Page post(Class<? extends IEventProcessingContext> context, String action, FormFields formFields, Object... actionParameters) {
		return post(Sails.eventContextName(context), action, formFields, actionParameters);
	}

	/**
	 * Performs an HTTP POST request
	 * 
	 * @return the index page of the current working controller
	 */
	public Page post(FormFields formFields, Object... parameters) {
		return post("index", formFields, parameters);
	}

	public Page post(String action, FormFields formFields, Object... parameters) {
		return post(workingContext(), action, formFields, parameters);
	}

	/**
	 * Performs an HTTP POST request
	 * <p>
	 * This is the 'fundamental' post method. It will not alter the working
	 * context. The other post methods, which take an
	 * {@link IEventProcessingContext} class, are what should be used unless
	 * there is no context class for the action you would like to get.
	 * 
	 * @param context the context identifier
	 * @param action
	 * @param parameters
	 * @return the page for the given context/action
	 */
	public Page post(String context, String action, FormFields formFields, Object... parameters) {
		TestPostEvent postEvent = createPostEvent(context, action, formFields, adaptParameters(parameters, new ContainerAdapterResolver(application.getContainer().instance(IAdapterResolver.class), requestContainer)));
		return doPost(postEvent);
	}

	/**
	 * Allows placement of <em>non-null</em> object into HttpSession.
	 * 
	 * @param value full name of getClass is used as attribute name
	 * @return the existing value replaced by new value
	 * @throws NullPointerException if value is null
	 */
	public Object setSessionAttribute(Object value) {
		return setSessionAttribute(value.getClass().getName(), value);
	}

	/**
	 * Causes an HttpSession to be created if it doesn't already exist and sets
	 * the provided attribute.
	 * 
	 * @param name
	 * @param value
	 * @return the existing value replaced by new value
	 */
	public Object setSessionAttribute(String name, Object value) {
		ShamHttpSession s = getSession(true);
		Object existing = s.getAttribute(name);
		s.setAttribute(name, value);
		return existing;
	}

	public void setWorkingContext(Class<? extends IEventProcessingContext> context) {
		this.workingContext = context;
	}

	public String workingContext() {
		return workingContext != null ? Sails.eventContextName(workingContext) : "home";
	}

	/**
	 * Performs an HTTP GET request to follow a redirect sent in a previous Page
	 * response
	 * <p>
	 * 
	 * 
	 * @param redirect the url to get to follow the http redirect command
	 * 
	 * @return the page for the given context/action
	 */
	public Page follow(TestRedirectUrl redirect) {
		return get(createGetEvent(redirect.toString()));
	}

}
