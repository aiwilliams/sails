package org.opensails.sails.component.oem;

import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.component.ComponentContainer;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.AbstractActionEventProcessor;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.viento.IBinding;

public class Component<I extends IComponentImpl> extends AbstractActionEventProcessor<I> implements IComponent {
	protected final String name;
	protected final ITemplateRenderer<IBinding> renderer;

	public Component(String name, Class<I> component, IAdapterResolver adapterResolver, ITemplateRenderer<IBinding> renderer) {
		super(component, adapterResolver);
		this.name = name;
		this.renderer = renderer;
	}

	public ComponentFactory createFactory(ISailsEvent event) {
		return new ComponentFactory(this, event);
	}

	public String getName() {
		return name;
	}

	public String getTemplatePath(String identifier) {
		return String.format("components/%s/%s", getName(), identifier);
	}

	public boolean isDestination(ISailsEvent event) {
		return event.getProcessorName().equals(String.format("component_%s", getName()));
	}

	/**
	 * Components live a double life as a type of Controller and as a reusable
	 * 'component'. Depending on what it is presently acting as, the container
	 * of the component will be one of two possibilities:
	 * <ol>
	 * <li>The container of the event</li>
	 * <li>A new, instance/component scoped container</li>
	 * </ol>
	 */
	@Override
	protected I createInstance(ISailsEvent event, Class<I> contextImpl) {
		I instance = null;
		if (isDestination(event)) {
			instance = event.getContainer().createEventContext(contextImpl, event);
			instance.setContainer(event.getContainer());
		} else {
			ComponentContainer componentContainer = new ComponentContainer(event.getContainer());
			instance = componentContainer.createEventContext(contextImpl, event);
			instance.setContainer(componentContainer);
			instance.configureContainer(componentContainer);
		}
		return instance;
	}

	@Override
	protected I createInstanceOrNull(ISailsEvent event) {
		I instance = super.createInstanceOrNull(event);
		instance.setTemplateRenderer(renderer);
		return instance;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected I createNullInstance(ISailsEvent event) {
		return (I) new NullComponent();
	}
}
