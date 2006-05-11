package org.opensails.component.tester;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.Sails;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.IComponentResolver;
import org.opensails.sails.tester.Page;
import org.opensails.sails.tester.browser.TesterGetEvent;
import org.opensails.viento.IBinding;

public class TestComponent<C extends IComponentImpl> {
	private final Class<? extends IComponentImpl> componentClass;
	private final TesterGetEvent event;
	private boolean hasBeenInitialized;
	private IComponentImpl instance;
	private final SailsComponentTester tester;

	public TestComponent(SailsComponentTester tester, TesterGetEvent event, Class<? extends IComponentImpl> componentClass) {
		this.tester = tester;
		this.event = event;
		this.componentClass = componentClass;
	}

	public IEventContextContainer getRequestContainer() {
		return event.getContainer();
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

	public Page render(Object... initializationArguments) {
		if (!hasBeenInitialized) initialize(initializationArguments);
		Page page = tester.get(event);
		page.source(); // cause render
		return page;
	}

	protected void exposeForUseInDynamicTemplate(IComponentImpl componentImpl) {
		IBinding binding = event.getContainer().instance(IBinding.class);
		binding.put("instance", componentImpl);
	}
}
