package org.opensails.sails.component.oem;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opensails.js.JavascriptMethodCall;
import org.opensails.js.JavascriptObject;
import org.opensails.js.Js;
import org.opensails.sails.Sails;
import org.opensails.sails.SailsException;
import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.component.Callback;
import org.opensails.sails.component.NotForJs;
import org.opensails.sails.component.Remembered;
import org.opensails.sails.html.HtmlGenerator;
import org.opensails.sails.html.Script;
import org.opensails.sails.tools.UrlforTool;
import org.opensails.sails.url.ActionUrl;
import org.opensails.spyglass.SpyClass;
import org.opensails.spyglass.SpyGlass;
import org.opensails.spyglass.policy.SpyPolicy;

public class ComponentScript extends Script {
	private final BaseComponent component;
	private final ContainerAdapterResolver adapterResolver;

	public ComponentScript(BaseComponent component, ContainerAdapterResolver adapterResolver) {
		this.adapterResolver = adapterResolver;
		this.component = component;
	}

	@SuppressWarnings("unchecked")
	public String render() {
		JavascriptObject extensions = Js.object();
		JavascriptMethodCall constructor = Js.methodCall(StringUtils.capitalize(name()), extensions);
		List<String> rememberedFields = new ArrayList<String>();
		for (Field field : fieldsForJs()) {
			try {
				IAdapter adapter = adapterResolver.resolve(field.getType());
				Object value = adapter.forWeb(new AdaptationTarget<Object>((Class<Object>) field.getType()), SpyGlass.read(component, field));
				extensions.set(field.getName(), value);
				if (field.isAnnotationPresent(Remembered.class)) rememberedFields.add(field.getName() + "=" + value);
			} catch (SailsException e) {
				// Couldn't adapt. This is ugly.
			}
		}
		String stringRemeberedFields = StringUtils.join(rememberedFields.iterator(), "&");

		UrlforTool urlfor = component.getContainer().instance(UrlforTool.class, UrlforTool.class);
		for (Method callback : callbacks()) {
			ActionUrl url = urlfor.action(Sails.eventContextName(component.getClass()), callback.getName());
			String method = callback.getAnnotation(Callback.class).method().toString().toLowerCase();
			extensions.set(callback.getName(), Js.methodCall("Component.callback", callback.getName(), url, Js.object("method", method, "parameters", stringRemeberedFields)));
		}

		for (Map.Entry<String, String> node : domNodes().entrySet())
			extensions.set(node.getKey(), Js.node(node.getValue()));
		
		return String.format("window.%s=new %s;", component.id, constructor);
	}

	protected String name() {
		return component.getComponentName();
	}

	protected Map<String, String> domNodes() {
		return component.domNodes;
	}

	protected Collection<Method> callbacks() {
		return spyClass().getMethodsAnnotated(Callback.class);
	}

	protected Collection<Field> fieldsForJs() {
		return spyClass().getFieldsNotAnnotated(NotForJs.class);
	}

	@SuppressWarnings("unchecked")
	protected SpyClass<? extends BaseComponent> spyClass() {
		return new SpyClass(component.getClass(), SpyPolicy.PUBLICONLY);
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		generator.write("\n");
		generator.write(render());
		generator.write("\n");
	}
}
