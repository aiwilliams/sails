package org.opensails.viento.builtins;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.opensails.viento.Name;

public class ObjectMixin {

	public List<Property> properties(Object target) {
		List<Property> properties = new ArrayList<Property>();
		
		for (Method method : target.getClass().getMethods())
			if (method.getParameterTypes().length == 0 && method.getReturnType() != Void.TYPE && method.getName().startsWith("get") && !method.getName().equals("getClass"))
				properties.add(new Property(target, method));
		return properties;
	}
	
	@Name("?")
	public Object silence(Object object) {
		return object;
	}
	
	public class Property {
		protected Object target;
		protected Method method;

		public Property(Object target, Method method) {
			this.target = target;
			this.method = method;
		}

		public String getName() {
			return Character.toLowerCase(method.getName().charAt(3)) + method.getName().substring(4);
		}
		
		public Object getValue() throws Exception {
			return method.invoke(target, new Object[0]);
		}
	}
}
