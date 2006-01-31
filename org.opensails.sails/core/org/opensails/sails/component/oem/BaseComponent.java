package org.opensails.sails.component.oem;

import org.opensails.rigging.*;
import org.opensails.sails.*;
import org.opensails.sails.component.*;
import org.opensails.sails.event.oem.*;
import org.opensails.sails.template.*;
import org.opensails.viento.*;

public class BaseComponent extends AbstractEventProcessingContext<IComponent> implements IComponentImpl {
	protected ScopedContainer container;
	protected ITemplateRenderer<IBinding> renderer;

	public String getComponentName() {
		return Sails.componentName(this.getClass());
	}

	@Override
	public ScopedContainer getContainer() {
		if (container == null) {
			container = super.getContainer();
			if (!isActingAsEventProcessor()) {
				container = super.getContainer().makeChild();
				container.makeLocal(IBinding.class);
				configureContainer(container);
				getBinding().mixin(this);
				getBinding().mixin(container.instance(IMixinResolver.class));
			}
		}
		return container;
	}

	@Override
	public String getTemplatePath(String identifier) {
		return String.format("components/%s/%s", getComponentName(), identifier);
	}
	
	/**
	 * Called on toString() from the template.
	 * 
	 * @return the output of the component
	 */
	protected String render() {
		String identifier = getTemplatePath("view");
		try {
			return renderer.render(identifier, getBinding()).toString();
		} catch (Exception e) {
			throw new TemplateRenderFailedException(identifier, e);
		}
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
		return event.getProcessorName().equals("component_" + getComponentName());
	}
}
