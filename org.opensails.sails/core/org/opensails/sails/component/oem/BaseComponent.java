package org.opensails.sails.component.oem;

import org.opensails.rigging.ScopedContainer;
import org.opensails.sails.Sails;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.event.oem.AbstractEventProcessingContext;
import org.opensails.sails.html.Script;
import org.opensails.sails.template.IMixinResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.TemplateRenderFailedException;
import org.opensails.viento.IBinding;

public class BaseComponent extends AbstractEventProcessingContext<IComponent> implements IComponentImpl {
	protected ScopedContainer container;
	protected ITemplateRenderer<IBinding> renderer;

	/**
	 * Override to configure the component container after creation.
	 * 
	 * @param container
	 */
	protected void configureContainer(ScopedContainer container) {}

	protected boolean isActingAsEventProcessor() {
		return event.getProcessorName().equals("component_" + getComponentName());
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

	public Script scriptInit() {
		return new ComponentScript(this);
	}

	public void setTemplateRenderer(ITemplateRenderer<IBinding> renderer) {
		this.renderer = renderer;
	}

	@Override
	public String toString() {
		return render();
	}
}
