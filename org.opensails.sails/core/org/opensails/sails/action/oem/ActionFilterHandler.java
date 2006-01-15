package org.opensails.sails.action.oem;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Stack;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.sails.SailsException;
import org.opensails.sails.action.ActionFilters;
import org.opensails.sails.action.ActionMethods;
import org.opensails.sails.action.AfterFilters;
import org.opensails.sails.action.BeforeFilters;
import org.opensails.sails.action.IActionFilter;
import org.opensails.sails.annotate.BehaviorInstance;
import org.opensails.sails.annotate.oem.BehaviorHandlerAdapter;
import org.opensails.sails.util.ClassHelper;

public class ActionFilterHandler extends BehaviorHandlerAdapter {
	Stack<AfterFilters> afterFilters = new Stack<AfterFilters>();
	Stack<BeforeFilters> beforeFilters = new Stack<BeforeFilters>();

	@SuppressWarnings("unchecked")
	public boolean add(BehaviorInstance instance) {
		Annotation behavior = instance.getAnnotation();
		if (behavior.annotationType() == BeforeFilters.class) beforeFilters.push((BeforeFilters) behavior);
		else afterFilters.push((AfterFilters) behavior);
		return true;
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean beforeAction(ActionInvocation invocation) {
		for (BeforeFilters filters : beforeFilters) {
			if (hasActionFilters(filters)) {
				for (ActionFilters classFilter : filters.actions()) {
					if (!excepted(invocation, classFilter) && inclusive(invocation, classFilter)) {
						for (Class<? extends IActionFilter> filter : classFilter.filters()) {
							IActionFilter actionFilter = ClassHelper.instantiate(filter);
							if (!actionFilter.beforeAction(invocation.getAction(), invocation.getContext())) return false;
						}
					}
				}
			}
			if (hasMethodFilters(filters)) {
				for (ActionMethods methodFilter : filters.methods()) {
					if (!excepted(invocation, methodFilter) && inclusive(invocation, methodFilter)) {
						for (String method : methodFilter.methods()) {
							// TODO: Support methods up heirarchy
							try {
								Method methodCode = invocation.getContextClass().getDeclaredMethod(method, ArrayUtils.EMPTY_CLASS_ARRAY);
								if (Modifier.isPrivate(methodCode.getModifiers())) throw new SailsException(String.format("Filter methods must be public or protected", method));
								methodCode.setAccessible(true);
								methodCode.invoke(invocation.getContext(), ArrayUtils.EMPTY_OBJECT_ARRAY);
							} catch (Exception e) {
								throw new SailsException("Failure invoking filter method", e);
							}
						}
					}
				}
			}
		}
		return true;
	}

	private boolean excepted(ActionInvocation invocation, ActionFilters classFilter) {
		return ArrayUtils.contains(classFilter.except(), invocation.getActionName());
	}

	private boolean excepted(ActionInvocation invocation, ActionMethods filter) {
		return ArrayUtils.contains(filter.except(), invocation.getActionName());
	}

	private boolean hasActionFilters(BeforeFilters filters) {
		return filters.actions().length > 1 || filters.actions()[0].filters()[0] != IActionFilter.class;
	}

	private boolean hasMethodFilters(BeforeFilters filters) {
		return filters.methods().length > 1 || (filters.methods()[0].methods().length == 1 && !filters.methods()[0].methods()[0].equals(""));
	}

	private boolean inclusive(ActionInvocation invocation, ActionFilters classFilter) {
		return (classFilter.only().length == 1 && classFilter.only()[0].equals("")) || ArrayUtils.contains(classFilter.only(), invocation.getActionName());
	}

	private boolean inclusive(ActionInvocation invocation, ActionMethods filter) {
		return (filter.only().length == 1 && filter.only()[0].equals("")) || ArrayUtils.contains(filter.only(), invocation.getActionName());
	}
}
