package org.opensails.sails.component.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.component.ComponentContainer;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.Remembered;
import org.opensails.sails.event.oem.AbstractEventProcessingContext;
import org.opensails.sails.form.IElementIdGenerator;
import org.opensails.sails.mixins.BuiltinScript;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.Require;
import org.opensails.sails.template.TemplateRenderFailedException;
import org.opensails.viento.IBinding;
import org.opensails.viento.IRenderable;

public abstract class BaseComponent extends AbstractEventProcessingContext<IComponent> implements IComponentImpl, IRenderable {
	/**
	 * The id of this component.
	 * <p>
	 * All components should have an id. This allows for multiple instances
	 * within a page.
	 */
	@Remembered
	public String id;
	protected IEventContextContainer container;
	protected Map<String, String> domNodes = new HashMap<String, String>();
	protected IElementIdGenerator idGenerator;
	protected ITemplateRenderer<IBinding> renderer;

	/**
	 * Conveniently implemented to do nothing. Override if you like.
	 */
	public void configureContainer(ComponentContainer container) {}

	public String getComponentName() {
		return processor.getName();
	}

	@Override
	public IEventContextContainer getContainer() {
		return container;
	}

	@Override
	public String getTemplatePath(String identifier) {
		return processor.getTemplatePath(identifier);
	}

	public String idfor(String name) {
		String id = idGenerator.idForStrings(this.id, name);
		domNodes.put(name, id);
		return id;
	}

	public void initialize(String id) {
		this.id = id;
	}

	/**
	 * @return the output of the component
	 */
	public String renderThyself() {
		String identifier = getTemplatePath("view");
		try {
			return renderer.render(identifier, getBinding()).toString();
		} catch (Exception e) {
			throw new TemplateRenderFailedException(identifier, e);
		}
	}

	public ComponentScript scriptInit() {
		getContainer().instance(Require.class).componentApplicationScript(new BuiltinScript(event).builtin("component.js"));
		getContainer().instance(Require.class).componentApplicationScript(new BuiltinScript(event).builtin("prototype.js"));
		return new ComponentScript(this, getContainer().instance(ContainerAdapterResolver.class));
	}

	public void setContainer(IEventContextContainer container) {
		this.container = container;
	}

	public void setIdGenerator(IElementIdGenerator generator) {
		this.idGenerator = generator;
	}

	public void setTemplateRenderer(ITemplateRenderer<IBinding> renderer) {
		this.renderer = renderer;
	}
}
