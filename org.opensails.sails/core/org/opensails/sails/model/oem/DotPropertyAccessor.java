package org.opensails.sails.model.oem;

import org.opensails.sails.adapter.AdaptationTarget;
import org.opensails.sails.model.IPropertyAccessor;
import org.opensails.sails.model.IPropertyPath;
import org.opensails.sails.model.PropertyAccessException;
import org.opensails.spyglass.InstanceProperty;
import org.opensails.spyglass.SpyObject;
import org.opensails.spyglass.policy.SpyPolicy;

public class DotPropertyAccessor<T> implements IPropertyAccessor {
	protected SpyPolicy accessPolicy;
	protected IPropertyPath path;

	/**
	 * Uses SpyPolicy.MEDIUM
	 * 
	 * @param path
	 */
	public DotPropertyAccessor(IPropertyPath path) {
		this(path, SpyPolicy.MEDIUM);
	}

	/**
	 * @param path
	 * @param accessPolicy
	 */
	public DotPropertyAccessor(IPropertyPath path, SpyPolicy accessPolicy) {
		this.path = path;
		this.accessPolicy = accessPolicy;
	}

	public Object get(Object model) throws PropertyAccessException {
		SpyObject result = new SpyObject<Object>(model, accessPolicy);
		for (String node : path.getNodes()) {
			result = result.readSpy(node);
			if (result == null) return null;
		}
		return result.getObject();
	}

	@SuppressWarnings("unchecked")
	public AdaptationTarget getAdaptationTarget(Object model) throws PropertyAccessException {
		SpyObject<Object> spyModel = new SpyObject<Object>(model, accessPolicy);
		InstanceProperty<Object> spyProperty = getProperty(spyModel, path);
		if (spyProperty == null) return new AdaptationTarget(null, null);
		return new AdaptationTarget(spyProperty.getType(), spyProperty.getGenericType());
	}

	protected InstanceProperty<Object> getProperty(SpyObject<Object> model, IPropertyPath path) {
		if (model == null) return null;
		InstanceProperty<Object> property = model.getProperty(path.getFirstProperty());
		if (path.getPropertyCount() > 1) return getProperty(property.getSpy(), path.dropModelNode());
		return property;
	}

	public void set(Object model, Object value) throws PropertyAccessException {
		SpyObject target = new SpyObject<Object>(model, accessPolicy);
		String[] nodes = path.getNodes();
		for (int i = 0; i < nodes.length - 1; i++)
			target = target.readSpy(nodes[i]);
		target.write(nodes[nodes.length - 1], value);
	}
}
