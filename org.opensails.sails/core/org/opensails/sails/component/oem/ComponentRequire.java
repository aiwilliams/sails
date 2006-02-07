package org.opensails.sails.component.oem;

import java.util.List;

import org.opensails.ezfile.EzPath;
import org.opensails.sails.IResourceResolver;
import org.opensails.sails.component.IComponent;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.sails.html.Script;
import org.opensails.sails.html.Style;
import org.opensails.sails.template.Require;
import org.opensails.sails.url.IUrl;
import org.opensails.sails.url.UrlType;

public class ComponentRequire {
	protected final IComponent component;
	protected final ISailsEvent event;
	protected final Require require;

	public ComponentRequire(ISailsEvent event, IComponent component, Require require, IResourceResolver resourceResolver) {
		this.event = event;
		this.component = component;
		this.require = require;

		IUrl script = event.resolve(UrlType.COMPONENT, relativeToComponent("script.js"));
		if (resourceResolver.exists(script)) require.componentImplicitScript(new Script(script));

		IUrl style = event.resolve(UrlType.COMPONENT, relativeToComponent("style.css"));
		if (resourceResolver.exists(style)) require.componentImplicitStyle(new Style(style));
	}

	public void script(String identifier) {
		if (applicationScope(identifier))
			require.componentApplicationScript(new Script(event.resolve(UrlType.SCRIPT, identifier)));
		else
			require.componentRequiredScript(new Script(event.resolve(UrlType.COMPONENT, relativeToComponent(identifier))));
	}

	public void scripts(List<String> identifiers) {
		for (String identifier : identifiers)
			script(identifier);
	}

	public void style(String identifier) {
		if (applicationScope(identifier))
			require.componentApplicationStyle(new Style(event.resolve(UrlType.STYLE, identifier)));
		else
			require.componentRequiredStyle(new Style(event.resolve(UrlType.COMPONENT, relativeToComponent(identifier))));
	}

	protected boolean applicationScope(String identifier) {
		return identifier.startsWith("/") || identifier.startsWith("\\");
	}

	protected String relativeToComponent(String path) {
		return EzPath.join(component.getName(), path);
	}
}
