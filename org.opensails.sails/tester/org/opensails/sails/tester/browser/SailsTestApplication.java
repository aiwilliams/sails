package org.opensails.sails.tester.browser;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.ApplicationScope;
import org.opensails.sails.Sails;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.controller.IControllerImpl;
import org.opensails.sails.event.IEventProcessingContext;
import org.opensails.sails.oem.BaseConfigurator;
import org.opensails.sails.oem.SailsApplication;
import org.opensails.sails.persist.IObjectPersister;
import org.opensails.sails.tester.SailsTesterConfigurator;
import org.opensails.sails.tester.TestApplicationContainer;
import org.opensails.sails.tester.oem.TestingDispatcher;
import org.opensails.sails.tester.oem.VirtualAdapterResolver;
import org.opensails.sails.tester.oem.VirtualControllerResolver;
import org.opensails.sails.tester.persist.IShamObjectPersister;
import org.opensails.sails.tester.servletapi.ShamServletConfig;
import org.opensails.sails.tester.servletapi.ShamServletContext;
import org.opensails.sails.util.BleedingEdgeException;
import org.opensails.spyglass.SpyObject;

/**
 * Makes a Sails application testable to a high degree.
 * <p>
 * This testing architecture is under development. Please use the SailsTester
 * until further notice.
 * 
 * @author aiwilliams
 */
public class SailsTestApplication extends SailsApplication {
	private static final long serialVersionUID = 5121573136618329350L;

	protected List<Browser> browsers = new ArrayList<Browser>();

	/**
	 * @see org.opensails.sails.ISailsApplicationConfigurator
	 * @param configurator the configurator to used
	 */
	public SailsTestApplication(Class<? extends BaseConfigurator> configurator) {
		initialize(configurator);
	}

	/**
	 * @see org.opensails.sails.ISailsApplicationConfigurator
	 * @param configurator the configurator to used
	 * @param contextRootDirectory
	 */
	public SailsTestApplication(Class<? extends BaseConfigurator> configurator, File contextRootDirectory) {
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
	 * This is written to return an IShamObjectPersister on purpose. If you are
	 * using a non-sham, please write a wrapper to place your non-sham in.
	 * 
	 * @return the current IShamObjectPersister
	 */
	public IShamObjectPersister getObjectPersister() {
		return (IShamObjectPersister) getContainer().instance(IObjectPersister.class);
	}

	public List<TestSession> getSessions() {
		throw new BleedingEdgeException("you should still be using SailsTester");
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
	 * Invalidates all sessions. Any references to the old HttpSession are not
	 * managed.
	 */
	public void invalidateSessions() {
		for (Browser browser : browsers)
			browser.invalidateSession();
	}

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

	/**
	 * Allows for the registration of customer IControllerImpls. Very useful
	 * when you want to test Sails itself ;)
	 * 
	 * @param <C>
	 * @param controller
	 */
	public <C extends IControllerImpl> void registerController(C controller) {
		getContainer().instance(VirtualControllerResolver.class).register(controller);
	}

	protected Browser createBrowser() {
		return new Browser(this);
	}

	protected TestingDispatcher getDispatcher() {
		return (TestingDispatcher) dispatcher;
	}

	protected void initialize(Class<? extends BaseConfigurator> configuratorClass) {
		initialize(configuratorClass, new File(Sails.DEFAULT_CONTEXT_ROOT_DIRECTORY));
	}

	protected void initialize(Class<? extends BaseConfigurator> configuratorClass, File contextRootDirectory) {
		initialize(configuratorClass, new ShamServletConfig(new ShamServletContext(contextRootDirectory)));
	}

	protected void initialize(Class<? extends BaseConfigurator> configuratorClass, ShamServletConfig config) {
		new SpyObject<SailsTestApplication>(this).write("config", config);
		configureAndStart(instrumentedConfigurator(configuratorClass));
	}

	protected SailsTesterConfigurator instrumentedConfigurator(Class<? extends BaseConfigurator> configuratorClass) {
		return new SailsTesterConfigurator(configuratorClass);
	}

}
