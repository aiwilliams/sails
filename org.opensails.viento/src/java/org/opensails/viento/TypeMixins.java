package org.opensails.viento;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class TypeMixins extends ObjectMethods {
	protected List<TypeMixin> mixins = new ArrayList<TypeMixin>();
	protected TypeMixins parent;
	
	public TypeMixins() {
	}
	
	public TypeMixins(TypeMixins parent) {
		this.parent = parent;
	}

	public CallableMethod find(TargetedMethodKey key) {
		Class[] mixinArgs = new Class[key.argClasses.length + 1];
		mixinArgs[0] = key.targetClass;
		System.arraycopy(key.argClasses, 0, mixinArgs, 1, key.argClasses.length);
		
		for (TypeMixin mixin : getMixins()) {
			if (mixin.getType().isAssignableFrom(key.targetClass)) {
				Method method = super.findAppropriateMethod(mixin.getMixin().getClass(), key.methodName, mixinArgs);
				if (method != null)
					return new MixinMethod(method, mixin.getMixin());
			}
		}

		return null;
	}
	
	public void add(Class type, Object mixin) {
		mixins.add(new TypeMixin(type, mixin));
	}
	
	protected List<TypeMixin> getMixins() {
		if (parent == null)
			return mixins;
		List<TypeMixin> allMixins = new ArrayList<TypeMixin>();
		allMixins.addAll(mixins);
		allMixins.addAll(parent.getMixins());
		return mixins;
	}
	
	public class MixinMethod extends ObjectMethod {
		private final Object mixin;

		public MixinMethod(Method method, Object mixin) {
			super(method);
			this.mixin = mixin;
		}
		
		@Override
		public Object call(Object target, Object[] args) {
			Object[] mixinArgs = new Object[args.length + 1];
			mixinArgs[0] = target;
			System.arraycopy(args, 0, mixinArgs, 1, args.length);

			return super.call(mixin, mixinArgs);
		}
		
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
