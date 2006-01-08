package org.opensails.sails.annotate.oem;

import java.lang.annotation.Annotation;

import org.opensails.sails.action.oem.ActionInvokation;
import org.opensails.sails.annotate.IBehaviorHandler;

public abstract class BehaviorHandlerAdapter<B extends Annotation> implements IBehaviorHandler<B> {
	public void afterAction(ActionInvokation invokation) {}

	public boolean beforeAction(ActionInvokation invokation) {
		return true;
	}
}
