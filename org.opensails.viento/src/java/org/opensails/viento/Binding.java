package org.opensails.viento;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensails.viento.builtins.LoopMixin;
import org.opensails.viento.builtins.EscapeMixin;
import org.opensails.viento.builtins.IfMixin;
import org.opensails.viento.builtins.PropertiesMixin;
import org.opensails.viento.builtins.SetMixin;
import org.opensails.viento.builtins.SilenceMixin;
import org.opensails.viento.builtins.WithMixin;

public class Binding {
	protected ExceptionHandler exceptionHandler;
	protected Map<String, Object> map;
	protected Binding parent;
	protected List<Object> topLevelMixins;
	protected List<TypeMixin> typeMixins;

	public Binding() {
		this(new HashMap<String, Object>());
	}

	public Binding(Binding parent) {
		this();
		this.parent = parent;
		setExceptionHandler(parent.exceptionHandler);
	}

	public Binding(Map<String, Object> map) {
		this.map = map;
		this.topLevelMixins = new ArrayList<Object>();
		this.typeMixins = new ArrayList<TypeMixin>();
		populateDefaults();
	}

	public Object call(Object target, String methodName) {
		return call(target, methodName, new Object[0]);
	}

	public Object call(Object target, String methodName, Object[] args) {
		return call(target, methodName, args, false);
	}

	public Object call(Object target, String methodName, Object[] args, boolean isSilent) {
		List<Throwable> failedAttempts = new ArrayList<Throwable>();
		try {
			return internalCall(target, methodName, args, failedAttempts);
		} catch (NoSuchMethodException e) {
			if (isSilent)
				return "";
			return exceptionHandler.resolutionFailed(target, methodName, args, failedAttempts);
		}
	}

	public Object call(String methodName) {
		return call(methodName, new Object[0]);
	}

	public Object call(String methodName, Object[] args) {
		return call(methodName, args, false);
	}
	
	public Object call(String methodName, Object[] args, boolean isSilent) {
		List<Throwable> failedAttempts = new ArrayList<Throwable>();
		try {
			return internalCall(methodName, args, failedAttempts);
		} catch (NoSuchMethodException e) {
			if (isSilent)
				return "";
			return exceptionHandler.resolutionFailed(methodName, args, failedAttempts);
		}
	}

	public void mixin(Class<?> target, Object helper) {
		getTypeMixins().add(new TypeMixin(target, helper));
	}

	public void mixin(Object helper) {
		topLevelMixins.add(helper);
	}

	public void put(String key, Object object) {
		map.put(key, object);
	}

	public void setExceptionHandler(ExceptionHandler exceptionHandler) {
		this.exceptionHandler = exceptionHandler;
	}

	protected Object callMethod(Object target, Method method, Object[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (method == null) throw new NoSuchMethodException();
		Object result = method.invoke(target, args);
		if (method.getReturnType() == Void.TYPE) return "";
		return result;
	}

	protected Method findAppropriateMethod(Object target, String methodName, Object[] args) {
		return findAppropriateMethod(target.getClass(), methodName, args);
	}

	protected Method findAppropriateMethod(Class<? extends Object> type, String methodName, Object[] args) {
		Method theMethod = null;
		Method[] methods = type.getMethods();
		for (Method method : methods)
			if (nameMatch(methodName, method) && method.getParameterTypes().length == args.length && typesMatch(method.getParameterTypes(), args, theMethod))
				theMethod = method;
		if (type.getSuperclass() != null && theMethod == null)
			return findAppropriateMethod(type.getSuperclass(), methodName, args);
		return theMethod;
	}

	protected String getter(String methodName) {
		return "get" + Character.toUpperCase(methodName.charAt(0)) + methodName.substring(1);
	}

	protected Object internalCall(Object target, String methodName, Object[] args, List<Throwable> failedAttempts) throws NoSuchMethodException {
		try {
			return mixins(target, methodName, args, failedAttempts);
		} catch (Exception e) {}

		try {
			return object(target, methodName, args);
		} catch (InvocationTargetException invocationTargetException) {
			failedAttempts.add(invocationTargetException.getCause());
		} catch (Exception e) {}

		try {
			return methodMissing(target, methodName, args);
		} catch (InvocationTargetException invocationTargetException) {
			failedAttempts.add(invocationTargetException.getCause());
		} catch (Exception e) {}
		
		throw new NoSuchMethodException();
	}
	
	protected Object mixins(Object target, String methodName, Object[] args, List<Throwable> failedAttempts) throws Exception {
		Object[] mixinArgs = new Object[args.length + 1];
		mixinArgs[0] = target;
		System.arraycopy(args, 0, mixinArgs, 1, args.length);
		for (TypeMixin mixin : getTypeMixins()) {
			if (mixin.getType().isAssignableFrom(target.getClass())) {
				try {
					return object(mixin.getMixin(), methodName, mixinArgs);
				} catch (InvocationTargetException invocationTargetException) {
					failedAttempts.add(invocationTargetException.getCause());
				} catch (Exception e) {}
			}
		}
		throw new NoSuchMethodException();
	}

	protected List<TypeMixin> getTypeMixins() {
		if (parent == null)
			return typeMixins;
		List<TypeMixin> allTypeMixins = new ArrayList<TypeMixin>();
		allTypeMixins.addAll(typeMixins);
		allTypeMixins.addAll(parent.getTypeMixins());
		return allTypeMixins;
	}
	
	protected Object object(Object target, String methodName, Object[] args) throws Exception {
		Method method = findAppropriateMethod(target, methodName, args);
		if (method != null) return callMethod(target, method, args);
		throw new NoSuchMethodException();
	}
	
	protected Object methodMissing(Object target, String methodName, Object[] args) throws Exception {
		if (target instanceof MethodMissing) return ((MethodMissing) target).methodMissing(methodName, args);
		else {
			Method methodMissing = target.getClass().getDeclaredMethod("methodMissing", new Class[] { String.class, Object[].class });
			if (methodMissing != null) return methodMissing.invoke(target, new Object[] { methodName, args });
		}
		throw new NoSuchMethodException();
	}

	protected Object internalCall(String methodName, Object[] args, List<Throwable> failedAttempts) throws NoSuchMethodException {
		for (Object helper : topLevelMixins) {
			try {
				return internalCall(helper, methodName, args, failedAttempts);
			} catch (Exception e) {}
		}
		if (args.length == 0 && map.containsKey(methodName)) return map.get(methodName);
		if (parent != null) return parent.internalCall(methodName, args, failedAttempts);
		throw new NoSuchMethodException();
	}

	protected boolean nameMatch(String methodName, Method method) {
		if (method.isAnnotationPresent(Name.class)) return method.getAnnotation(Name.class).value().equals(methodName);
		return method.getName().equals(methodName) || method.getName().equals(getter(methodName));
	}

	protected boolean nullMatch(Class<?> type, Object object) {
		return !type.isPrimitive() && object == null;
	}

	protected void populateDefaults() {
		setExceptionHandler(new DefaultExceptionHandler());

		mixin(new IfMixin());
		mixin(new EscapeMixin());
		mixin(new SilenceMixin());
		mixin(new WithMixin());
		mixin(new SetMixin(this));
		mixin(Collection.class, new LoopMixin());
		mixin(Object[].class, new LoopMixin());
		mixin(Object.class, new PropertiesMixin());
	}

	protected boolean primitiveMatch(Class<?> type, Object object) {
		return ((type == boolean.class && object.getClass() == Boolean.class) || (type == char.class && object.getClass() == Character.class)
				|| (type == byte.class && object.getClass() == Byte.class) || (type == short.class && object.getClass() == Short.class)
				|| (type == int.class && object.getClass() == Integer.class) || (type == long.class && object.getClass() == Long.class)
				|| (type == float.class && object.getClass() == Float.class) || (type == double.class && object.getClass() == Double.class));
	}

	protected boolean typesMatch(Class<?>[] parameterTypes, Object[] args, Method theMethod) {
		for (int i = 0; i < parameterTypes.length; i++)
			if (!nullMatch(parameterTypes[i], args[i]) && !parameterTypes[i].isAssignableFrom(args[i].getClass()) && !primitiveMatch(parameterTypes[i], args[i]) || (theMethod != null && parameterTypes[i].isAssignableFrom(theMethod.getParameterTypes()[i]))) return false;
		return true;
	}

	class TypeMixin {
		protected Object mixin;
		protected Class<?> type;

		public TypeMixin(Class<?> type, Object mixin) {
			this.type = type;
			this.mixin = mixin;
		}

		public Object getMixin() {
			return mixin;
		}

		public Class<?> getType() {
			return type;
		}
	}
}
