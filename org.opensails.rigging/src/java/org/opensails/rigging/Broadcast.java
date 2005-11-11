package org.opensails.rigging;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collection;

public class Broadcast {

	@SuppressWarnings("unchecked")
	public static <T> T to(Class<T> type, Collection<T> instances) {
		return (T) Proxy.newProxyInstance(type.getClassLoader(), new Class[] {type}, new BroadcastInvocationHandler(instances));
	}
	
	public static class BroadcastInvocationHandler implements InvocationHandler {
		private final Collection<?> instances;

		public BroadcastInvocationHandler(Collection<?> instances) {
			this.instances = instances;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			for (Object object : instances)
				method.invoke(object, args);
			return null;
		}
	}
}
