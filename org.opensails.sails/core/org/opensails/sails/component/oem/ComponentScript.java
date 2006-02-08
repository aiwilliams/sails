package org.opensails.sails.component.oem;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.opensails.sails.Sails;
import org.opensails.sails.component.Callback;
import org.opensails.sails.component.Remembered;
import org.opensails.sails.html.IInlineContent;
import org.opensails.sails.html.Script;
import org.opensails.sails.mixins.UrlforMixin;
import org.opensails.sails.util.ClassHelper;

public class ComponentScript extends Script implements IInlineContent {
	private final BaseComponent component;

	public ComponentScript(BaseComponent component) {
		this.inlineContent = this;
		this.component = component;
	}

	public String render() {
		StringBuilder buffer = new StringBuilder();
		buffer.append("window.");
		buffer.append(component.id);
		buffer.append(" = new ");
		buffer.append(StringUtils.capitalize(component.getComponentName()));
		buffer.append("({");
		
		List<Parameter> parameters = new ArrayList<Parameter>();
		Field[] fields = component.getClass().getFields();
		for (Field field : fields) {
			try {
				// TODO: Don't quote non strings
				parameters.add(new Parameter(field.getName(), "'" + field.get(component) + "'"));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		List<String> rememberedFields = new ArrayList<String>();
		for (Field field : ClassHelper.fieldsAnnotated(component.getClass(), Remembered.class))
			rememberedFields.add(field.getName() + "=" + ClassHelper.readField(component, field));
		String stringRemeberedFields = StringUtils.join(rememberedFields.iterator(), "&");
		
		UrlforMixin urlfor = component.getContainer().instance(UrlforMixin.class, UrlforMixin.class);
		Method[] callbacks = ClassHelper.methodsAnnotated(component.getClass(), Callback.class);
		for (Method callback : callbacks) {
			String name = callback.getName();
			StringBuilder callbackBuffer = new StringBuilder();
			callbackBuffer.append("Component.callback('");
			callbackBuffer.append(name);
			callbackBuffer.append("', '");
			callbackBuffer.append(urlfor.action(Sails.eventContextName(component.getClass()), name));
			callbackBuffer.append("', {method: '");
			callbackBuffer.append(callback.getAnnotation(Callback.class).method().toString().toLowerCase());
			callbackBuffer.append("', parameters: '");
			callbackBuffer.append(stringRemeberedFields);
			callbackBuffer.append("'})");
			parameters.add(new Parameter(name,  callbackBuffer.toString()));
		}
		
		for (Map.Entry<String, String> node : component.domNodes.entrySet())
			parameters.add(new Parameter(node.getKey(), "$('" + node.getValue() + "')"));

		buffer.append(StringUtils.join(parameters.iterator(), ", "));
		buffer.append("});");
		return buffer.toString();
	}
	
	class Parameter {
		String name;
		Object value;
		public Parameter(String name, Object value) {
			this.name = name;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return name + ": " + value;
		}
	}
}
