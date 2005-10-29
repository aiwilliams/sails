package org.opensails.sails.template.viento;

import java.util.List;
import java.util.Map;

import org.opensails.sails.helper.oem.ThrowableMixin;
import org.opensails.sails.template.IExceptionHandler;
import org.opensails.sails.template.ITemplateBinding;
import org.opensails.viento.Binding;
import org.opensails.viento.ExceptionHandler;

public class VientoBinding extends Binding implements ITemplateBinding {
	public VientoBinding() {
		super();
	}

	public VientoBinding(IExceptionHandler exceptionHandler) {
		setExceptionHandler(exceptionHandler);
	}

	public VientoBinding(Map<String, Object> map) {
		super(map);
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
			public Object resolutionFailed(String methodName, Object[] args, List<Throwable> failedAttempts) {
				return exceptionHandler.resolutionFailed(methodName, args, failedAttempts);
			}

			public Object resolutionFailed(Object target, String methodName, Object[] args, List<Throwable> failedAttempts) {
				return exceptionHandler.resolutionFailed(target, methodName, args, failedAttempts);
			}
		});
	}
}
