package org.opensails.sails.component.oem;

import org.opensails.sails.adapter.IAdapterResolver;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.event.oem.AbstractActionEventProcessor;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.viento.IBinding;

public class Component<I extends IComponentImpl> extends AbstractActionEventProcessor<I> implements IComponent {
	protected final ITemplateRenderer<IBinding> renderer;

	public Component(Class<I> component, IAdapterResolver adapterResolver, ITemplateRenderer<IBinding> renderer) {
		super(component, adapterResolver);
		this.renderer = renderer;
	}

	public ComponentFactory createFactory(ISailsEvent event) {
		return new ComponentFactory(this, event);
	}

	@Override
	protected I createInstanceOrNull(ISailsEvent event) {
		if (!hasImplementation()) return null;
		I instance = super.createInstanceOrNull(event);
		instance.setTemplateRenderer(renderer);
		return instance;
	}
}
