package org.opensails.viento;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.opensails.viento.builtins.DoMixin;
import org.opensails.viento.builtins.EscapeMixin;
import org.opensails.viento.builtins.IfMixin;
import org.opensails.viento.builtins.LoopMixin;
import org.opensails.viento.builtins.ObjectMixin;
import org.opensails.viento.builtins.SetMixin;
import org.opensails.viento.builtins.SilenceMixin;

public class Binding implements IBinding, IMethodResolver {
	protected ExceptionHandler exceptionHandler;
	protected IBinding parent;
	// protected Cache cache;
	protected TypeMixins typeMixins;
	protected TopLevelMixins topLevelMixins;
	protected ObjectMethods methods;
	// protected MethodMissingResolver methodMissing;
	protected List<IMethodResolver> dynamicMethodResolvers;
	protected List<IObjectResolver> dynamicResolvers;
	protected Statics statics;

	public Binding() {
		this(new NullBinding());
	}

	public Binding(IBinding parent) {
		this.parent = parent;
		populateDefaults();
	}

	public void addMethodResolver(IMethodResolver resolver) {
		dynamicMethodResolvers.add(resolver);
	}
	
	public void addObjectResolver(IObjectResolver resolver) {
		dynamicResolvers.add(resolver);
	}

	public Object call(CallableMethod method, MethodKey key, Object target, Object[] args, int line, int offset) {
		if (method == null) return new UnresolvableObject(exceptionHandler, null, key, target, args, line, offset, false);
		// cache.put(key, method);
		Object result = null;
		try {
			result = method.call(target, args);
		} catch (Throwable t) {
			return new UnresolvableObject(exceptionHandler, t, key, target, args, line, offset, false);
		}

		if (result == null) return new UnresolvableObject(exceptionHandler, null, key, target, args, line, offset, true);
		return result;
	}

	public Object call(Object target, String methodName) {
		return call(target, methodName, new Object[0]);
	}

	public Object call(Object target, String methodName, Object[] args) {
		return call(target, methodName, args, 0, 0);
	}

	public Object call(Object target, String methodName, Object[] args, int line, int offset) {
		TargetedMethodKey key = new TargetedMethodKey(target.getClass(), methodName, getClasses(args));
		return call(find(key), key, target, args, line, offset);
	}

	public Object call(String methodName) {
		return call(methodName, new Object[0]);
	}

	public Object call(String methodName, Object[] args) {
		return call(methodName, args, 0, 0);
	}

	public Object call(String methodName, Object[] args, int line, int offset) {
		TopLevelMethodKey key = new TopLevelMethodKey(methodName, getClasses(args));
		return call(find(key), key, null, args, line, offset);
	}

	public boolean canResolve(String name) {
		return statics.find(new TopLevelMethodKey(name, new Class[0])) != null;
	}

	public Binding createChild() {
		return new Binding(this);
	}

	public CallableMethod find(TargetedMethodKey key) {
		CallableMethod callableMethod = null;
		Iterator<IMethodResolver> orderedResolvers = orderedMethodResolvers().iterator();
		while (callableMethod == null && orderedResolvers.hasNext())
			callableMethod = orderedResolvers.next().find(key);
		return callableMethod;
	}

	public CallableMethod find(TopLevelMethodKey key) {
		CallableMethod callableMethod = null;
		Iterator<IObjectResolver> orderedResolvers = orderedObjectResolvers().iterator();
		while (callableMethod == null && orderedResolvers.hasNext())
			callableMethod = orderedResolvers.next().find(key);
		return callableMethod;
	}

	public ExceptionHandler getExceptionHandler() {
		return exceptionHandler;
	}

	public void mixin(Class<?> target, Object mixin) {
		typeMixins.add(target, mixin);
	}

	public void mixin(Object mixin) {
		topLevelMixins.add(mixin);
	}

	public void put(String key, Object object) {
		statics.put(key, object);
	}

	public void putAll(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet())
			put(entry.getKey(), entry.getValue());
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	// public void add(DynamicResolver dynamicResolver) {
	// dynamicResolvers.add(dynamicResolver);
	// }

	protected Class[] getClasses(Object[] args) {
		Class[] classes = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null) classes[i] = null;
			else classes[i] = args[i].getClass();
		}
		return classes;
	}

	protected List<IMethodResolver> orderedMethodResolvers() {
		List<IMethodResolver> ordered = new ArrayList<IMethodResolver>();
		// ordered.add(cache);
		ordered.add(methods);
		ordered.add(typeMixins);
		ordered.addAll(dynamicMethodResolvers);
		ordered.add(parent);
		return ordered;
	}

	protected List<IObjectResolver> orderedObjectResolvers() {
		List<IObjectResolver> ordered = new ArrayList<IObjectResolver>();
		// ordered.add(cache);
		ordered.add(statics);
		ordered.add(topLevelMixins);
		ordered.addAll(dynamicResolvers);
		ordered.add(parent);
		return ordered;
	}

	protected void populateDefaults() {
		// cache = new Cache();
		dynamicMethodResolvers = new ArrayList<IMethodResolver>();
		dynamicResolvers = new ArrayList<IObjectResolver>();
		topLevelMixins = new TopLevelMixins();
		methods = new ObjectMethods();
		// methodMissing = new MethodMissingResolver();
		statics = new Statics();

		setExceptionHandler(parent.getExceptionHandler());
		typeMixins = new TypeMixins();

		mixin(new IfMixin());
		mixin(new EscapeMixin());
		mixin(new SilenceMixin());
		mixin(new DoMixin());
		mixin(new SetMixin(this));
		mixin(Collection.class, new LoopMixin());
		mixin(Object[].class, new LoopMixin());
		mixin(Object.class, new ObjectMixin());
	}
}
