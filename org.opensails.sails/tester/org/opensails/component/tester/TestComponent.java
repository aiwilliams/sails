package org.opensails.component.tester;

import java.io.InputStream;

import org.apache.tools.ant.filters.StringInputStream;
import org.opensails.sails.RequestContainer;
import org.opensails.sails.Sails;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.IComponentResolver;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.TestGetEvent;
import org.opensails.viento.IBinding;

public class TestComponent<C extends IComponentImpl> {
	private final Class<? extends IComponentImpl> componentClass;
	private final SailsComponentTester tester;
	private final TestGetEvent event;
	private IComponentImpl instance;
	private boolean hasBeenInitialized;

	public TestComponent(SailsComponentTester tester, TestGetEvent event, Class<? extends IComponentImpl> componentClass) {
		this.tester = tester;
		this.event = event;
		this.componentClass = componentClass;
	}

	protected void exposeForUseInDynamicTemplate(IComponentImpl componentImpl) {
		event.getContainer().instance(IBinding.class).put("instance", componentImpl);
	}

	@SuppressWarnings("unchecked")
	public C initialize(Object... arguments) {
		hasBeenInitialized = true;
		IComponentResolver resolver = tester.getContainer().instance(IComponentResolver.class);
		IComponent component = resolver.resolve(Sails.componentName(componentClass));
		instance = component.createFactory(event).create(arguments);
		exposeForUseInDynamicTemplate(instance);
		return (C) instance;
	}

	/**
	 * @return an InputStream that is a 'template' that refers to this component
	 */
	public InputStream referringTemplateContent() {
		return new StringInputStream("$instance");
	}

	public Page render(Object... initializationArguments) {
		if (!hasBeenInitialized) initialize(initializationArguments);
		Page page = tester.doGet(event);
		page.source(); // cause render
		return page;
	}

	public RequestContainer getRequestContainer() {
		return event.getContainer();
	}
}
