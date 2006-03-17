package org.opensails.sails.annotate.filter;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.action.BeforeFilter;
import org.opensails.sails.action.BeforeFilters;
import org.opensails.sails.action.oem.ActionInvocation;
import org.opensails.sails.annotate.BehaviorInstance;
import org.opensails.sails.annotate.oem.BehaviorHandlerAdapter;
import org.opensails.spyglass.SpyGlass;

public class ActionFilterHandler extends BehaviorHandlerAdapter {
	List<Annotation> afterFilters = new ArrayList<Annotation>();
	List<Annotation> beforeFilters = new ArrayList<Annotation>();

	@SuppressWarnings("unchecked")
	public boolean add(BehaviorInstance instance) {
		Annotation behavior = instance.getAnnotation();
		if (behavior.annotationType() == BeforeFilters.class) beforeFilters.add(0, behavior);
		else if (behavior.annotationType() == BeforeFilter.class) beforeFilters.add(0, behavior);
		else afterFilters.add(0, behavior);
		return true;
	}

	@Override
	public void afterAction(ActionInvocation invocation) {
		for (Annotation filters : afterFilters)
			obtainFilter(filters, invocation).after();
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean beforeAction(ActionInvocation invocation) {
		for (Annotation filterDeclaration : beforeFilters) {
			Object result = obtainFilter(filterDeclaration, invocation).before();
			if (result == null || result.equals(true)) continue;
			if (!result.equals(false)) throw new IllegalStateException("Return value other than null, true, false not supported yet");
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	private IFilter obtainFilter(Annotation filterDeclaration, ActionInvocation invocation) {
		FilterBuilder builderAnnotation = filterDeclaration.annotationType().getAnnotation(FilterBuilder.class);
		Class<? extends IFilterBuilder> builderClass = builderAnnotation.value();
		IFilterBuilder builder = SpyGlass.instantiate(builderClass);
		return builder.build(filterDeclaration, invocation);
	}
}
