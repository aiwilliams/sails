package org.opensails.sails.tester.browser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.Sails;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.configurator.SailsConfigurator;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.oem.SailsApplication;
import org.opensails.sails.persist.IIdentifiable;
import org.opensails.sails.persist.IObjectPersister;
import org.opensails.sails.tester.TestApplicationConfigurator;
import org.opensails.sails.tester.TestApplicationContainer;
import org.opensails.sails.tester.oem.TestingDispatcher;
import org.opensails.sails.tester.oem.VirtualAdapterResolver;
import org.opensails.sails.tester.oem.VirtualControllerResolver;
import org.opensails.sails.tester.persist.ITestObjectPersister;
import org.opensails.sails.tester.servletapi.ShamServletConfig;
import org.opensails.sails.tester.servletapi.ShamServletContext;
import org.opensails.spyglass.SpyObject;

/**
 * Makes a Sails application testable to a high degree.
 * 
 * @see Browser to understand how to use this class
 * @author aiwilliams
 */
public class SailsTestApplication extends SailsApplication {
	private static final long serialVersionUID = 5121573136618329350L;

	protected List<Browser> browsers = new ArrayList<Browser>();

	/**
	 * @see org.opensails.sails.ISailsApplicationConfigurator
	 * @param configurator the configurator to used
	 */
	public SailsTestApplication(Class<? extends SailsConfigurator> configurator) {
		initialize(configurator);
	}

	/**
	 * @see org.opensails.sails.ISailsApplicationConfigurator
	 * @param configurator the configurator to used
	 * @param contextRootDirectory
	 */
	public SailsTestApplication(Class<? extends SailsConfigurator> configurator, File contextRootDirectory) {
		initialize(configurator, contextRootDirectory);
	}

	/**
	 * Useful for subclassing.
	 */
	protected SailsTestApplication() {}

	/**
	 * @return the application container as a TestApplicationContainer
	 */
	public TestApplicationContainer getContainer() {
		return (TestApplicationContainer) applicationContainer;
	}

	/**
	 * Provides access to the IObjectPersister of the application under test.
	 * <p>
	 * This is written to return an ITestObjectPersister on purpose. If you are
	 * using a non-test persister, please write a wrapper to place your non-test
	 * persister in.
	 * 
	 * @return the current ITestObjectPersister
	 */
	public ITestObjectPersister getObjectPersister() {
		return (ITestObjectPersister) getContainer().instance(IObjectPersister.class);
	}

	/**
	 * @return active sessions from the current application browsers
	 */
	public List<TestSession> getSessions() {
		List<TestSession> sessions = new ArrayList<TestSession>(browsers.size());
		for (Browser browser : browsers) {
			TestSession session = browser.getSession();
			if (session != null) sessions.add(session);
		}
		return sessions;
	}

	/**
	 * Places the component implementation into the application dependency
	 * injection container.
	 * <p>
	 * This is handy when writing tests where you want to control the
	 * implementation class for a particular component. Note that you may also
	 * provide an instance.
	 * 
	 * @see #inject(Class, Object)
	 * 
	 * @param <T> the class type of the implementation
	 * @param component
	 * @param implementation
	 */
	public <T> void inject(Class<? super T> component, Class<T> implementation) {
		inject(component, implementation, ApplicationScope.REQUEST);
	}

	/**
	 * Places the component implementation into the dependency injection
	 * container at the specified scope.
	 * 
	 * @see #inject(Class, Class)
	 * 
	 * @param <T> the class type of the implementation
	 * @param component
	 * @param implementation
	 * @param scope
	 */
	public <T> void inject(Class<? super T> component, Class<T> implementation, ApplicationScope scope) {
		getContainer().getContainerInHierarchy(scope).inject(component, implementation);
	}

	/**
	 * Places the component instance into the application dependency injection
	 * container.
	 * 
	 * @see #inject(Class, Class)
	 * 
	 * @param <T> the class type of the implementation
	 * @param component
	 * @param instance
	 */
	public <T> void inject(Class<? super T> component, T instance) {
		inject(component, instance, ApplicationScope.REQUEST);
	}

	/**
	 * Places the component instance into the dependency injection container at
	 * the specified scope.
	 * 
	 * @see #inject(Class, Class)
	 * 
	 * @param <T> the class type of the implementation
	 * @param component
	 * @param instance
	 * @param scope
	 */
	public <T> void inject(Class<? super T> component, T instance, ApplicationScope scope) {
		getContainer().getContainerInHierarchy(scope).inject(component, instance);
	}

	/**
	 * Invalidates all sessions. Any references to the old HttpSession are not
	 * managed.
	 */
	public void invalidateSessions() {
		for (Browser browser : browsers)
			browser.invalidateSession();
	}

	/**
	 * A Browser provides an API for testing a Sails application 'out of
	 * container'.
	 * <p>
	 * You can open as many as you like. Each one will have it's own session,
	 * etc.
	 * 
	 * @return new Browser for this
	 */
	public Browser openBrowser() {
		Browser browser = createBrowser();
		browsers.add(browser);
		return browser;
	}

	/**
	 * @param <C>
	 * @param workingContext
	 * @return new Browser with workingContext
	 */
	public <C extends IEventProcessingContext> Browser openBrowser(Class<C> workingContext) {
		Browser browser = openBrowser();
		browser.setWorkingContext(workingContext);
		return browser;
	}

	/**
	 * Places models into current {@link ITestObjectPersister} for use in
	 * testing interactions with persistence.
	 * 
	 * @see #getObjectPersister()
	 * @param models
	 */
	public void provides(IIdentifiable... models) {
		getObjectPersister().provides(models);
	}

	/**
	 * Allows tests to provide the IAdapter Class for a model type.
	 * <p>
	 * This does not allow instances as I (aiwilliams) don't really see a need
	 * for that. It seems best to me to provide an adapter in the same form that
	 * the framework would discover them: as classes.
	 * 
	 * @param <T>
	 * @param modelType
	 * @param adapter
	 */
	@SuppressWarnings("unchecked")
	public <T> void registerAdapter(Class<T> modelType, Class<? extends IAdapter<T, ?>> adapter) {
		getContainer().instance(VirtualAdapterResolver.class).register(modelType, adapter);
	}

	public void registerBrowser(Browser browser) {
		browsers.add(browser);
	}

	public void registerController(Class<? extends IControllerImpl> controller) {
		getVirtualControllerResolver().register(controller);
	}

	/**
	 * Allows for the registration of custom IControllerImpl instances.
	 * 
	 * @param <C>
	 * @param controller
	 * @see #registerController(String, IControllerImpl)
	 */
	public <C extends IControllerImpl> void registerController(C controller) {
		getVirtualControllerResolver().register(controller);
	}

	private VirtualControllerResolver getVirtualControllerResolver() {
		return getContainer().instance(VirtualControllerResolver.class);
	}

	/**
	 * Allows for the registration of custom IControllerImpl instances. Very
	 * useful when you want to test Sails itself ;)
	 * <p>
	 * The instance version of this allows you to have state setup for the
	 * controller in your tests. If you do this, you MUST either create a new
	 * instance for each event and re-register it, or just re-register it if you
	 * know it can be used twice. It is likely that you would need to clear the
	 * result of the controller instance so that proper behavior occurs.
	 * 
	 * @param <C>
	 * @param controllerName
	 * @param controller
	 */
	public <C extends IControllerImpl> void registerController(String controllerName, C controller) {
		getVirtualControllerResolver().register(controllerName, controller);
	}

	protected Browser createBrowser() {
		return new Browser(this);
	}

	protected TestingDispatcher getDispatcher() {
		return (TestingDispatcher) dispatcher;
	}

	protected void initialize(Class<? extends SailsConfigurator> configuratorClass) {
		initialize(configuratorClass, new File(Sails.DEFAULT_CONTEXT_ROOT_DIRECTORY));
	}

	protected void initialize(Class<? extends SailsConfigurator> configuratorClass, File contextRootDirectory) {
		initialize(configuratorClass, new ShamServletConfig(new ShamServletContext(contextRootDirectory)));
	}

	protected void initialize(Class<? extends SailsConfigurator> configuratorClass, ShamServletConfig config) {
		SpyObject.create(this).write("config", config);
		configureAndStart(instrumentedConfigurator(configuratorClass));
	}

	protected TestApplicationConfigurator instrumentedConfigurator(Class<? extends SailsConfigurator> configuratorClass) {
		return new TestApplicationConfigurator(configuratorClass);
	}

}
