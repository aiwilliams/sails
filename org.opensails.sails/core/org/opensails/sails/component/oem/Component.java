package org.opensails.sails.component.oem;

import org.opensails.sails.adapter.IAdapterResolver;
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

	@Override
	protected I createInstanceOrNull(ISailsEvent event) {
		if (!hasImplementation()) return null;
		I instance = isDestination(event) ? event.getContainer().create(processingContext, event) : event.getContainer().instance(processingContext, processingContext);
		instance.setEventContext(event, this);
		instance.setTemplateRenderer(renderer);
		return instance;
	}
}
