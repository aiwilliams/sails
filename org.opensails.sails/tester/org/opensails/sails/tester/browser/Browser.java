package org.opensails.sails.tester.browser;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.sails.ApplicationScope;
import org.opensails.sails.Sails;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.configurator.IEventConfigurator;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.GetEvent;
import org.opensails.sails.event.oem.PostEvent;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.oem.Dispatcher;
import org.opensails.sails.template.viento.VientoTemplateRenderer;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.TestApplicationContainer;
import org.opensails.sails.tester.oem.TestingHttpServletResponse;
import org.opensails.sails.tester.oem.VirtualResourceResolver;
import org.opensails.sails.tester.servletapi.ShamHttpServletRequest;
import org.opensails.sails.tester.servletapi.ShamHttpSession;

/**
 * Simulates a browser connected to a Sails application. These are obtained from
 * a SailsTestApplication.
 * <p>
 * This testing architecture is under development. Please use the SailsTester
 * until further notice.
 * <p>
 * If you are writing lots of tests against one application, and only care to
 * have one Browser (most likely), then it is probably best to create a subclass
 * of this that initializes itself by creating it's application.
 * 
 * @author aiwilliams
 */
public class Browser {
	/**
	 * When set to true, the next request created will be marked as multipart.
	 * 
	 * @see ShamFormFields#multipart()
	 */
	public boolean nextRequestIsMultipart;
	protected Dispatcher eventDispatcher;
	protected TestRequestContainer requestContainer;
	protected ShamHttpSession session;
	protected Class<? extends IEventProcessingContext> workingContext;
	protected SailsTestApplication application;

	protected Browser() {}

	protected Browser(SailsTestApplication application) {
		initialize(application);
	}

	/**
	 * If you would like to have an event, but not have a controller or
	 * controller directory with templates, you can use this to establish the
	 * environment necessary to make this application think that the controller
	 * and action exist.
	 * 
	 * @param eventPath
	 * @param templateContent
	 * @return a TestGetEvent that is configured by the ISailsEventConfigurator
	 *         and has the given eventPath
	 */
	public TestGetEvent createVirtualEvent(String eventPath, String templateContent) {
		TestGetEvent event = createGetEvent(eventPath);
		getContainer().instance(IEventConfigurator.class).configure(event, event.getContainer());
		VirtualResourceResolver resourceResolver = getContainer().instance(VirtualResourceResolver.class);
		resourceResolver.register(eventPath + VientoTemplateRenderer.TEMPLATE_IDENTIFIER_EXTENSION, templateContent);
		return event;
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
	public Page get(Class<? extends IEventProcessingContext> context, String action, Object... parameters) {
		this.workingContext = context;
		return get(action, parameters);
	}

	public Page get(GetEvent event) {
		eventDispatcher.dispatch(event);
		prepareForNextRequest();
		return createPage(event);
	}

	public Page get(String action) {
		return get(workingContext(), action, ArrayUtils.EMPTY_OBJECT_ARRAY);
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
	public Page get(String action, Object... parameters) {
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
	public Page get(String context, String action, Object... parameters) {
		TestGetEvent event = createGetEvent(context, action, adaptParameters(parameters));
		return get(event);
	}

	/**
	 * @return the application under test
	 */
	public SailsTestApplication getApplication() {
		return application;
	}

	/**
	 * @return the container of the application
	 */
	public TestApplicationContainer getApplicationContainer() {
		return application.getContainer();
	}

	/**
	 * @return the RequestContainer that will be used for the next event
	 */
	public TestRequestContainer getContainer() {
		return requestContainer;
	}

	/**
	 * Provides FormFields with methods that help you set up for a request.
	 * <p>
	 * You should get a new one of these for each request you expect to post.
	 * 
	 * @return a new instance of ShamFormFields
	 */
	public ShamFormFields getFormFields() {
		return new ShamFormFields(this, adapterResolver());
	}

	public TestSession getSession() {
		return new TestSession(this);
	}

	/**
	 * @return the current session. If create, creates one. If not, returns
	 *         null.
	 */
	public TestSession getSession(boolean create) {
		return new TestSession(this, true);
	}

	/**
	 * Performs a get and renders the provided templateContent.
	 * 
	 * @param templateContent
	 * @return the rendered page
	 */
	public Page getTemplated(String templateContent) {
		return get(createVirtualEvent("dynamicallyGeneratedInSailsTester/getTemplated", templateContent));
	}

	public <T> void inject(Class<? super T> key, Class<T> implementation) {
		inject(key, implementation, ApplicationScope.REQUEST);
	}

	public <T> void inject(Class<? super T> key, Class<T> implementation, ApplicationScope scope) {
		getContainer().getContainerInHierarchy(scope).inject(key, implementation);
	}

	public <T> void inject(Class<? super T> key, T instance) {
		inject(key, instance, ApplicationScope.REQUEST);
	}

	public <T> void inject(Class<? super T> key, T instance, ApplicationScope scope) {
		getContainer().getContainerInHierarchy(scope).inject(key, instance);
	}

	/**
	 * Invalidates the current HttpSession. Any references to the old
	 * HttpSession are not managed.
	 */
	public void invalidateSession() {
		session.invalidate();
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
		TestPostEvent postEvent = createPostEvent(context, action, formFields, adaptParameters(parameters));
		return post(postEvent);
	}

	/**
	 * A super-handy way to render arbitrary templates with no action code.
	 * 
	 * @param templateContent
	 * @return the resulting Page
	 */
	public Page render(String templateContent) {
		return get(createVirtualEvent("browser/render", templateContent));
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
		TestSession s = getSession(true);
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

	protected ContainerAdapterResolver adapterResolver() {
		return new ContainerAdapterResolver(getContainer().instance(IAdapterResolver.class), requestContainer);
	}

	protected String[] adaptParameters(Object... parameters) {
		return adaptParameters(parameters, adapterResolver());
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
		TestGetEvent event = new TestGetEvent(application, requestContainer, request, response);
		requestContainer.bind(event);
		response.set(event);
		return event;
	}

	protected TestGetEvent createGetEvent(String context, String action, String... parameters) {
		return createGetEvent(toPathInfo(context, action, parameters));
	}

	protected Page createPage(ISailsEvent event) {
		return new Page(event);
	}

	protected TestPostEvent createPostEvent(String pathInfo, FormFields formFields) {
		ShamHttpServletRequest request = createRequest();
		request.setPathInfo(pathInfo);
		request.setParameters(formFields.toMap());
		TestingHttpServletResponse response = new TestingHttpServletResponse();
		TestPostEvent event = new TestPostEvent(application, requestContainer, request, response);
		requestContainer.bind(event);
		response.set(event);
		return event;
	}

	protected TestPostEvent createPostEvent(String context, String action, FormFields formFields, String... parameters) {
		return createPostEvent(toPathInfo(context, action, parameters), formFields);
	}

	/**
	 * @return a request that is bound to this browser such that when a session
	 *         is created, we have it
	 */
	protected ShamHttpServletRequest createRequest() {
		ShamHttpServletRequest request = new ShamHttpServletRequest(Browser.this.session) {
			@Override
			public javax.servlet.http.HttpSession getSession() {
				return Browser.this.session = (ShamHttpSession) super.getSession();
			};

			@Override
			public javax.servlet.http.HttpSession getSession(boolean create) {
				return Browser.this.session = (ShamHttpSession) super.getSession(create);
			};
		};
		request.multipart = nextRequestIsMultipart;
		nextRequestIsMultipart = false;
		return request;
	}

	protected Page get(TestGetEvent event) {
		event.getContainer().registerAll(getContainer());
		return get((GetEvent) event);
	}

	protected ShamHttpSession getHttpSession(boolean create) {
		if (session == null && create) session = new ShamHttpSession();
		return session;
	}

	protected void initialize(SailsTestApplication application) {
		this.application = application;
		this.eventDispatcher = application.getDispatcher();
		prepareForNextRequest();
	}

	protected Page post(PostEvent event) {
		eventDispatcher.dispatch(event);
		prepareForNextRequest();
		return createPage(event);
	}

	// TODO: Bind to lazy created session container
	protected void prepareForNextRequest() {
		if (requestContainer != null) requestContainer = new TestRequestContainer(application.getContainer(), requestContainer.injections);
		else requestContainer = new TestRequestContainer(application.getContainer());
	}

	protected String toParametersString(String... parameters) {
		StringBuilder string = new StringBuilder();
		for (String param : parameters) {
			string.append("/");
			string.append(param);
		}
		return string.toString();
	}

	/**
	 * @param context
	 * @param action
	 * @param parameters
	 * @return
	 */
	protected String toPathInfo(String context, String action, String... parameters) {
		StringBuilder pathInfo = new StringBuilder();
		pathInfo.append(context);
		pathInfo.append("/");
		pathInfo.append(action);
		pathInfo.append(toParametersString(parameters));
		return pathInfo.toString();
	}
}
