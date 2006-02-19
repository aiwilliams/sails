package org.opensails.viento;

import java.util.Collection;
import java.util.Map;

import org.opensails.viento.builtins.DoMixin;
import org.opensails.viento.builtins.EscapeMixin;
import org.opensails.viento.builtins.IfMixin;
import org.opensails.viento.builtins.LoopMixin;
import org.opensails.viento.builtins.ObjectMixin;
import org.opensails.viento.builtins.SetMixin;
import org.opensails.viento.builtins.SilenceMixin;

public class Binding implements IBinding {
	protected ExceptionHandler exceptionHandler;
	protected Binding parent;
//	protected Cache cache;
	protected TypeMixins typeMixins;
	protected TopLevelMixins topLevelMixins;
	protected ObjectMethods methods;
//	protected MethodMissingResolver methodMissing;
	protected Statics statics;
//	protected List<DynamicResolver> dynamicResolvers;

	public Binding() {
		this(null);
	}

	public Binding(Binding parent) {
		this.parent = parent;
		populateDefaults();
	}

	public Object call(CallableMethod method, MethodKey key, Object target, Object[] args, int line, int offset) {
		if (method == null)
			return new UnresolvableObject(exceptionHandler, null, key, target, args, line, offset, false);
//		cache.put(key, method);
		Object result = null;
		try {
			result = method.call(target, args);
		} catch (Throwable t) {
			return new UnresolvableObject(exceptionHandler, t, key, target, args, line, offset, false);
		}
		
		if (result == null)
			return new UnresolvableObject(exceptionHandler, null, key, target, args, line, offset, true);
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
		return call(findMethod(key), key, target, args, line, offset);
	}
	
	public Object call(String methodName) {
		return call(methodName, new Object[0]);
	}

	public Object call(String methodName, Object[] args) {
		return call(methodName, args, 0, 0);
	}

	public Object call(String methodName, Object[] args, int line, int offset) {
		TopLevelMethodKey key = new TopLevelMethodKey(methodName, getClasses(args));
		return call(findMethod(key), key, null, args, line, offset);
	}

	public boolean canResolve(String name) {
		return statics.find(new TopLevelMethodKey(name, new Class[0])) != null;
	}

	protected CallableMethod findMethod(TargetedMethodKey key) {
//		CallableMethod method = cache.find(key);
//		if (method != null)
//			return method;
		CallableMethod method = methods.find(key);
		if (method != null)
			return method;
		method = typeMixins.find(key);
		if (method != null)
			return method;
//		method = methodMissing.find(key);
//		if (method != null)
//			return method;
		if (parent != null)
			return parent.findMethod(key);
		return null;
	}
	
	protected CallableMethod findMethod(TopLevelMethodKey key) {
//		CallableMethod method = cache.find(key);
//		if (method != null)
//			return method;
		CallableMethod method = statics.find(key);
		if (method != null)
			return method;
		method = topLevelMixins.find(key);
		if (method != null)
			return method;
//		for (DynamicResolver dynamicResolver : dynamicResolvers) {
//			method = dynamicResolver.find(key);
//			if (method != null)
//				return method;
//		}
		if (parent != null)
			return parent.findMethod(key);
		return null;
	}
	
	protected Class[] getClasses(Object[] args) {
		Class[] classes = new Class[args.length];
		for (int i = 0; i < args.length; i++) {
			if (args[i] == null)
				classes[i] = null;
			else
				classes[i] = args[i].getClass();
		}
		return classes;
	}

	public void mixin(Class<?> target, Object mixin) {
		typeMixins.add(target, mixin);
	}

	public void mixin(Object mixin) {
		topLevelMixins.add(mixin);
	}

	protected void populateDefaults() {
//		cache = new Cache();
//		dynamicResolvers = new ArrayList<DynamicResolver>();
		topLevelMixins = new TopLevelMixins();
		methods = new ObjectMethods();
//		methodMissing = new MethodMissingResolver();
		statics = new Statics();

		if (parent != null) {
			setExceptionHandler(parent.exceptionHandler);
			typeMixins = new TypeMixins(parent.typeMixins);
		} else {
			typeMixins = new TypeMixins();
			setExceptionHandler(new DefaultExceptionHandler());
		}

		mixin(new IfMixin());
		mixin(new EscapeMixin());
		mixin(new SilenceMixin());
		mixin(new DoMixin());
		mixin(new SetMixin(this));
		mixin(Collection.class, new LoopMixin());
		mixin(Object[].class, new LoopMixin());
		mixin(Object.class, new ObjectMixin());
	}
	
	public void put(String key, Object object) {
		statics.put(key, object);
	}
	
//	public void add(DynamicResolver dynamicResolver) {
//		dynamicResolvers.add(dynamicResolver);
//	}

	public void putAll(Map<String, Object> map) {
		for (Map.Entry<String, Object> entry : map.entrySet())
			put(entry.getKey(), entry.getValue());
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}
}
