package org.opensails.sails.annotate.oem;

import java.lang.annotation.Annotation;

import org.opensails.sails.action.oem.ActionInvocation;
import org.opensails.sails.annotate.IBehaviorHandler;

public abstract class BehaviorHandlerAdapter<B extends Annotation> implements IBehaviorHandler<B> {
	public void afterAction(ActionInvocation invocation) {}

	public boolean beforeAction(ActionInvocation invocation) {
		return true;
	}
}
