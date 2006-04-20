package org.opensails.sails.template;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.opensails.rigging.IContainer;
import org.opensails.sails.event.ISailsEvent;
import org.opensails.spyglass.IClassResolver;
import org.opensails.spyglass.SpyObject;
import org.opensails.spyglass.resolvers.PackageClassResolver;
import org.opensails.viento.CallableMethod;
import org.opensails.viento.IMethodResolver;
import org.opensails.viento.MixinMethod;
import org.opensails.viento.TargetedMethodKey;

public class MixinResolver implements IMethodResolver {
	protected final List<IClassResolver<Object>> resolvers;
	private final IContainer container;

	public MixinResolver(IContainer container) {
		this.container = container;
		this.resolvers = new ArrayList<IClassResolver<Object>>();
	}

	public MixinResolver(ISailsEvent event) {
		this(event.getContainer());
	}

	/**
	 * Pushes on a Java package within which searching should be done for
	 * mixins.
	 * 
	 * @param javaPackage
	 */
	public void push(String javaPackage) {
		resolvers.add(0, new PackageClassResolver<Object>(javaPackage, "Mixin"));
	}

	/**
	 * Pushes on an IClassResolver to use when searching for mixins.
	 * <p>
	 * This is useful if you would like to search for classes suffixed with
	 * something other than 'Mixin'.
	 * 
	 * @param resolver
	 */
	public void push(IClassResolver<Object> resolver) {
		resolvers.add(0, resolver);
	}

	public CallableMethod find(TargetedMethodKey key) {
		Class[] argsToMixins = new Class[key.argClasses.length + 1];
		System.arraycopy(key.argClasses, 0, argsToMixins, 1, key.argClasses.length);
		argsToMixins[0] = key.targetClass;
		
		for (IClassResolver<Object> resolver : resolvers) {
			Class<Object> mixinClass = resolver.resolve(key.targetClass);
			if (mixinClass != null) {
				Object mixin = container.instance(mixinClass, mixinClass);
				SpyObject<Object> spyMixin = SpyObject.create(mixin);
				Method methodTaking = spyMixin.getSpyClass().getSpyMethod(key.methodName).getMethodTaking(argsToMixins);
				if (methodTaking != null) return new MixinMethod(methodTaking, mixin);
			}
		}
		return null;
	}
}
