package org.opensails.sails.template.viento;

import org.opensails.sails.helper.oem.ThrowableMixin;
import org.opensails.sails.template.IExceptionHandler;
import org.opensails.sails.template.ITemplateBinding;
import org.opensails.viento.Binding;
import org.opensails.viento.ExceptionHandler;
import org.opensails.viento.TargetedMethodKey;
import org.opensails.viento.TopLevelMethodKey;

public class VientoBinding extends Binding implements ITemplateBinding {
	public VientoBinding() {
		super();
	}

	public VientoBinding(IExceptionHandler exceptionHandler) {
		setExceptionHandler(exceptionHandler);
	}

	public VientoBinding(VientoBinding parent) {
		super(parent);
	}
	
	@Override
	protected void populateDefaults() {
		super.populateDefaults();
		mixin(Throwable.class, new ThrowableMixin());
	}

	public void setExceptionHandler(final IExceptionHandler exceptionHandler) {
		setExceptionHandler(new ExceptionHandler() {
			public Object resolutionFailed(TargetedMethodKey key, Object target, Object[] args) {
				return exceptionHandler.resolutionFailed(target, key.methodName, args);
			}

			public Object resolutionFailed(TopLevelMethodKey key, Object[] args) {
				return exceptionHandler.resolutionFailed(key.methodName, args);
			}
		});
	}
}
