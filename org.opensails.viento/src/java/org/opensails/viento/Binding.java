package org.opensails.viento;

import java.util.Collection;
import java.util.Map;

import org.opensails.viento.builtins.DoMixin;
import org.opensails.viento.builtins.EscapeMixin;
import org.opensails.viento.builtins.IfMixin;
import org.opensails.viento.builtins.LoopMixin;
import org.opensails.viento.builtins.PropertiesMixin;
import org.opensails.viento.builtins.SetMixin;
import org.opensails.viento.builtins.SilenceMixin;
import org.opensails.viento.builtins.WithMixin;

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

	public Object call(Object target, String methodName) {
		return call(target, methodName, new Object[0]);
	}

	public Object call(Object target, String methodName, Object[] args) {
		return call(target, methodName, args, false, 0, 0);
	}

	public Object call(Object target, String methodName, Object[] args, boolean isSilent, int line, int offset) {
		if (target == null)
			return exceptionHandler.nullTarget(methodName, args, line, offset);
		TargetedMethodKey key = new TargetedMethodKey(target.getClass(), methodName, getClasses(args));
		return call(findMethod(key), key, target, args, isSilent, line, offset);
	}
	
	public boolean canResolve(String name) {
		return statics.find(new TopLevelMethodKey(name, new Class[0])) != null;
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

	protected CallableMethod findMethod(TargetedMethodKey key) {
//		CallableMethod method = cache.find(key);
//		if (method != null)
//			return method;
		CallableMethod method = typeMixins.find(key);
		if (method != null)
			return method;
		method = methods.find(key);
		if (method != null)
			return method;
//		method = methodMissing.find(key);
//		if (method != null)
//			return method;
		if (parent != null)
			return parent.findMethod(key);
		return null;
	}

	public Object call(String methodName) {
		return call(methodName, new Object[0]);
	}

	public Object call(String methodName, Object[] args) {
		return call(methodName, args, false, 0, 0);
	}
	
	public Object call(String methodName, Object[] args, boolean isSilent, int line, int offset) {
		TopLevelMethodKey key = new TopLevelMethodKey(methodName, getClasses(args));
		return call(findMethod(key), key, null, args, isSilent, line, offset);
	}
	
	public Object call(CallableMethod method, MethodKey key, Object target, Object[] args, boolean isSilent, int line, int offset) {
		if (method == null) {
			if (isSilent)
				return "";
			return resolutionFailed(key, target, args, line, offset);
		}
//		cache.put(key, method);
		Object result = null;
		try {
			result = method.call(target, args);
		} catch (Throwable t) {
			if (isSilent)
				return "";
			return resolutionFailed(t, key, target, args, line, offset);
		}
		
		if (result == null) {
			if (isSilent)
				return "";
			// Can't just blow up
			// TODO: implement UnresolvedReference
//			return resolutionFailed(key, target, args, line, offset);
		}
		return result;
	}

	protected Object resolutionFailed(MethodKey key, Object target, Object[] args, int line, int offset) {
		if (key instanceof TargetedMethodKey)
			return exceptionHandler.resolutionFailed((TargetedMethodKey)key, target, args, line, offset);
		return exceptionHandler.resolutionFailed((TopLevelMethodKey)key, args, line, offset);
	}

	protected Object resolutionFailed(Throwable exception, MethodKey key, Object target, Object[] args, int line, int offset) {
		if (key instanceof TargetedMethodKey)
			return exceptionHandler.resolutionFailed(exception, (TargetedMethodKey)key, target, args, line, offset);
		return exceptionHandler.resolutionFailed(exception, (TopLevelMethodKey)key, args, line, offset);
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
	
//	public void add(DynamicResolver dynamicResolver) {
//		dynamicResolvers.add(dynamicResolver);
//	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
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
		mixin(new WithMixin());
		mixin(new DoMixin());
		mixin(new SetMixin(this));
		mixin(Collection.class, new LoopMixin());
		mixin(Object[].class, new LoopMixin());
		mixin(Object.class, new PropertiesMixin());
	}
}
