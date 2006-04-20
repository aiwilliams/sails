package org.opensails.viento;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TypeMixins extends ObjectMethods {
	protected List<TypeMixin> mixins = new ArrayList<TypeMixin>();

	public TypeMixins() {}

	public CallableMethod find(TargetedMethodKey key) {
		Class[] mixinArgs = new Class[key.argClasses.length + 1];
		mixinArgs[0] = key.targetClass;
		System.arraycopy(key.argClasses, 0, mixinArgs, 1, key.argClasses.length);

		for (TypeMixin mixin : getMixins()) {
			if (mixin.getType().isAssignableFrom(key.targetClass)) {
				Method method = super.findAppropriateMethod(mixin.getMixin().getClass(), key.methodName, mixinArgs);
				if (method != null) return new MixinMethod(method, mixin.getMixin());
			}
		}

		return null;
	}

	public void add(Class type, Object mixin) {
		mixins.add(new TypeMixin(type, mixin));
	}

	protected List<TypeMixin> getMixins() {
		return mixins;
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

		@Override
		public String toString() {
			return String.format("Extending [%s]s with behavior of [%s]", type, mixin.getClass());
		}
	}
}
