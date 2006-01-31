package org.opensails.sails.mixins;

import java.util.List;

import org.opensails.sails.IResourceResolver;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.component.IComponentResolver;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.html.Script;
import org.opensails.sails.html.Style;
import org.opensails.sails.template.Require;
import org.opensails.sails.template.Require.RequireOutput;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;
import org.opensails.viento.IBinding;

public class RequireMixin {
	private final IBinding binding;
	private final IComponentResolver componentResolver;
	private final ISailsEvent event;
	private final Require require = new Require();
	private final IResourceResolver resourceResolver;

	public RequireMixin(ISailsEvent event, IResourceResolver resourceResolver, IBinding binding, IComponentResolver componentResolver) {
		this.event = event;
		this.resourceResolver = resourceResolver;
		this.binding = binding;
		this.componentResolver = componentResolver;
	}

	public void component(String identifier) {
		IUrl script = event.resolve(UrlType.COMPONENT, identifier + "/script.js");
		if (resourceResolver.exists(script)) componentScript(script);

		IUrl style = event.resolve(UrlType.COMPONENT, identifier + "/style.css");
		if (resourceResolver.exists(style)) style(style);

		IComponent component = componentResolver.resolve(identifier);
		binding.put(identifier, component.createFactory(event));
	}

	public RequireOutput output() {
		return require.output();
	}

	public void script(IUrl url) {
		require.script(new Script(url));
	}

	public void script(String identifier) {
		script(event.resolve(UrlType.SCRIPT, identifier));
	}

	public void scripts(List<String> identifiers) {
		for (String identifier : identifiers)
			script(identifier);
	}

	public void style(IUrl url) {
		require.style(new Style(url));
	}

	public void style(String identifier) {
		style(event.resolve(UrlType.STYLE, identifier));
	}

	@Override
	public String toString() {
		return output().toString();
	}

	private void componentScript(IUrl url) {
		require.componentScript(new Script(url));
	}
}
