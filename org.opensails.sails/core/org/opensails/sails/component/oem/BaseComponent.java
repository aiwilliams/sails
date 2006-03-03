package org.opensails.sails.component.oem;

import java.util.HashMap;
import java.util.Map;

import org.opensails.rigging.IScopedContainer;
import org.opensails.sails.IEventContextContainer;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.component.ComponentContainer;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentImpl;
import org.opensails.sails.component.Remembered;
import org.opensails.sails.event.oem.AbstractEventProcessingContext;
import org.opensails.sails.form.IFormElementIdGenerator;
import org.opensails.sails.mixins.BuiltinScript;
import org.opensails.sails.template.IMixinResolver;
import org.opensails.sails.template.ITemplateRenderer;
import org.opensails.sails.template.Require;
import org.opensails.sails.template.TemplateRenderFailedException;
import org.opensails.viento.IBinding;
import org.opensails.viento.IRenderable;

public class BaseComponent extends AbstractEventProcessingContext<IComponent> implements IComponentImpl, IRenderable {
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
    protected IFormElementIdGenerator idGenerator;
    protected ITemplateRenderer<IBinding> renderer;

    public String getComponentName() {
        return processor.getName();
    }

    /**
     * <ol>
     * <li>When acting as an IEventProcessingContext, the invoked action will
     * want to reference the container of the event</li>
     * <li>When acting as a generator in the render of the template of another
     * action, should be a component-scoped container</li>
     * </ol>
     */
    @Override
    public IEventContextContainer getContainer() {
        if (container == null) {
            container = event.getContainer();
            if (!processor.isDestination(event)) {
                container = new ComponentContainer(container);
                container.makeLocal(IBinding.class);
                configureContainer(container);
                IBinding binding = getBinding();
                binding.mixin(this);
                binding.mixin(container.instance(IMixinResolver.class));
            }
        }
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

    public void setIdGenerator(IFormElementIdGenerator generator) {
        this.idGenerator = generator;
    }

    public void setTemplateRenderer(ITemplateRenderer<IBinding> renderer) {
        this.renderer = renderer;
    }

    /**
     * Override to configure the component container after creation.
     * 
     * @param container
     */
    protected void configureContainer(IScopedContainer container) {}
}
