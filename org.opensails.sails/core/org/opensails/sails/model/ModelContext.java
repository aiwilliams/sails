package org.opensails.sails.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.opensails.spyglass.SpyGlass;

/**
 * Provides access to multiple model instances by name.
 * <p>
 * Since we don't have a binding like Ruby does, we need another way. This is
 * it. A model context is basically a map of objects, but it goes a bit further.
 * A model can be found:
 * <ol>
 * <li>by name</li>
 * <li>by {@link IPropertyPath#getModelName()}. This does not walk any
 * heirarchy.</li>
 * </ol>
 * 
 * @author aiwilliams
 */

/*
 * TODO Make this a Viento DynamicResolver, once that is implemented
 */
public class ModelContext {
	protected Map<String, Object> context = new HashMap<String, Object>();

	/**
	 * Expose a model, using the lower-camel class name of the instance as the
	 * name.
	 * 
	 * @param model
	 * @return the instance previously exposed as name
	 */
	public Object expose(Object model) {
		if (model == null) return null;
		return expose(SpyGlass.lowerCamelName(model.getClass()), model);
	}

	/**
	 * Expose a model by name.
	 * 
	 * @param name
	 * @param model
	 * @return the instance previously exposed as name
	 */
	public Object expose(String name, Object model) {
		return context.put(name, model);
	}

	/**
	 * @param path
	 * @return the instance exposed as path#getModelName() or null
	 */
	public Object getModel(IPropertyPath path) {
		return getModel(path.getModelName());
	}

	/**
	 * @param name
	 * @return the instance exposed as name or null
	 */
	public Object getModel(String name) {
		return context.get(name);
	}

	public Set<Map.Entry<String, Object>> getModelEntries() {
		return context.entrySet();
	}

	@Override
	public String toString() {
		return context.toString();
	}
}
