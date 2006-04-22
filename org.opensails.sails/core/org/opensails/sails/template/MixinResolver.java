package org.opensails.sails.template;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.ApplicationContainer;
import org.opensails.sails.configurator.IPackageDescriptor;
import org.opensails.spyglass.ClassKeyMappings;
import org.opensails.spyglass.IClassResolver;
import org.opensails.spyglass.SpyObject;
import org.opensails.spyglass.resolvers.PackageClassResolver;
import org.opensails.viento.CallableMethod;
import org.opensails.viento.IMethodResolver;
import org.opensails.viento.MixinMethod;
import org.opensails.viento.TargetedMethodKey;

/**
 * The MixinResolver is plugged into Viento to provide type-specific behaviorial
 * extensions.
 * <p>
 * Your mixins can be placed in any package answered by the
 * {@link IPackageDescriptor#getMixinPackages()}. They will be instatiated once
 * and may depend on anything available in the ApplicationContainer. They must
 * be thread safe, as only one instance will be created of each, only upon first
 * use.
 * <p>
 * This wants {@link IClassResolver}s, Java Package instances, or package names
 * that contain mixins. It will match first by looking at the Package
 * Annotations, consulting any that are {@link ClassKeyMappings}. If none are
 * present, or the Package cannot be loaded, it will search by name. The search
 * occurs top down, using the first mixin found that has the desired method
 * taking the provided arguments.
 * <p>
 * Please remember that the first argument to all of your mixin's methods must
 * be the type or super-type of the instance being extended.
 * 
 * @author aiwilliams
 */
public class MixinResolver implements IMethodResolver {
	protected final List<IClassResolver<Object>> resolvers;
	private final ApplicationContainer container;

	public MixinResolver(ApplicationContainer container) {
		this.container = container;
		this.resolvers = new ArrayList<IClassResolver<Object>>();
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

	/**
	 * Pushes on a Java package within which searching should be done for
	 * mixins.
	 * 
	 * @param javaPackage
	 */
	public void push(Package javaPackage) {
		resolvers.add(0, new PackageClassResolver<Object>(javaPackage, "Mixin"));
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
}
