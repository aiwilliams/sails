package org.opensails.viento;

import java.lang.reflect.Method;

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