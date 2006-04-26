package org.opensails.sails.component.oem;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opensails.js.JavascriptMethodCall;
import org.opensails.js.JavascriptObject;
import org.opensails.js.Js;
import org.opensails.sails.Sails;
import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.adapter.IAdapter;
import org.opensails.sails.component.Callback;
import org.opensails.sails.component.Remembered;
import org.opensails.sails.html.HtmlGenerator;
import org.opensails.sails.html.Script;
import org.opensails.sails.tools.UrlforTool;
import org.opensails.sails.url.ActionUrl;
import org.opensails.sails.util.ClassHelper;
import org.opensails.spyglass.SpyGlass;

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
		JavascriptMethodCall constructor = Js.methodCall(StringUtils.capitalize(component.getComponentName()), extensions);
		Field[] fields = component.getClass().getFields();
		List<String> rememberedFields = new ArrayList<String>();
		for (Field field : fields) {
			IAdapter adapter = adapterResolver.resolve(field.getType());
			Object forWeb = adapter.forWeb(new AdaptationTarget<Object>((Class<Object>) field.getType()), SpyGlass.read(component, field));
			extensions.set(field.getName(), forWeb);
			if (field.isAnnotationPresent(Remembered.class)) rememberedFields.add(field.getName() + "=" + forWeb);
		}
		String stringRemeberedFields = StringUtils.join(rememberedFields.iterator(), "&");

		UrlforTool urlfor = component.getContainer().instance(UrlforTool.class, UrlforTool.class);
		Method[] callbacks = ClassHelper.methodsAnnotated(component.getClass(), Callback.class);
		for (Method callback : callbacks) {
			String name = callback.getName();
			ActionUrl url = urlfor.action(Sails.eventContextName(component.getClass()), name);
			String method = callback.getAnnotation(Callback.class).method().toString().toLowerCase();
			extensions.set(name, Js.methodCall("Component.callback", name, url, Js.object("method", method, "parameters", stringRemeberedFields)));
		}

		for (Map.Entry<String, String> node : component.domNodes.entrySet())
			extensions.set(node.getKey(), Js.node(node.getValue()));
		
		return String.format("window.%s=new %s;", component.id, constructor);
	}

	@Override
	protected void body(HtmlGenerator generator) throws IOException {
		generator.write("\n");
		generator.write(render());
		generator.write("\n");
	}
}
