package org.opensails.sails.component.oem;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.Sails;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.event.oem.AbstractEventProcessingContext;
import org.opensails.sails.template.IMixinResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.viento.IBinding;

public class BaseComponent extends AbstractEventProcessingContext<IComponent> implements IComponentImpl {
	protected ScopedContainer container;
	protected ITemplateRenderer<IBinding> renderer;

	@Override
	public ScopedContainer getContainer() {
		if (container == null) {
			container = super.getContainer();
			if (!isActingAsEventProcessor()) {
				container = super.getContainer().makeChild();
				container.makeLocal(IBinding.class);
				configureContainer(container);
				getBinding().mixin(container.instance(IMixinResolver.class));
			}
		}
		return container;
	}

	public String getName() {
		return Sails.componentName(this.getClass());
	}

	public String render() {
		return renderer.render(String.format("components/%s/view", getName()), getBinding()).toString();
	}

	public void setTemplateRenderer(ITemplateRenderer<IBinding> renderer) {
		this.renderer = renderer;
	}

	@Override
	public String toString() {
		return render();
	}

	/**
	 * Override to configure the component container after creation.
	 * 
	 * @param container
	 */
	protected void configureContainer(ScopedContainer container) {}

	protected boolean isActingAsEventProcessor() {
		return event.getProcessorName().equals(getName());
	}
}
