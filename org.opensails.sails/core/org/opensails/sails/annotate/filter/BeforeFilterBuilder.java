package org.opensails.sails.annotate.filter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.sails.SailsException;
import org.opensails.sails.action.BeforeFilter;
import org.opensails.sails.action.IActionFilter;
import org.opensails.sails.action.oem.ActionInvocation;
import org.opensails.spyglass.SpyGlass;

public class BeforeFilterBuilder implements IFilterBuilder<BeforeFilter> {
	public IFilter build(BeforeFilter filterDeclaration, ActionInvocation invocation) {
		return new BeforeFilterFilter(filterDeclaration, invocation);
	}

	static class BeforeFilterFilter implements IFilter {
		private final BeforeFilter filterDeclaration;
		private final ActionInvocation invocation;

		protected BeforeFilterFilter(BeforeFilter filterDeclaration, ActionInvocation invocation) {
			this.filterDeclaration = filterDeclaration;
			this.invocation = invocation;
		}

		public void after() {}

		@SuppressWarnings("unchecked")
		public Object before() {
			if (hasActionFilters(filterDeclaration)) {
				if (!excepted(invocation, filterDeclaration) && inclusive(invocation, filterDeclaration)) {
					Object[] args = {};
					IActionFilter actionFilter = SpyGlass.instantiate(filterDeclaration.filter(), args);
					if (!actionFilter.beforeAction(invocation.getAction(), invocation.getContext())) return false;
				}
			}
			if (hasMethodFilters(filterDeclaration)) {
				if (!excepted(invocation, filterDeclaration) && inclusive(invocation, filterDeclaration)) {
					// TODO: Support methods up heirarchy
					try {
						Method methodCode = SpyGlass.getMethod(invocation.getContextClass(), filterDeclaration.method());
						if (Modifier.isPrivate(methodCode.getModifiers())) throw new SailsException(String.format("Filter methods must be public or protected", filterDeclaration.method()));
						methodCode.setAccessible(true);
						return methodCode.invoke(invocation.getContext(), ArrayUtils.EMPTY_OBJECT_ARRAY);
					} catch (Exception e) {
						throw new SailsException("Failure invoking filter method", e);
					}
				}
			}

			return true;
		}

		private boolean excepted(ActionInvocation invocation, BeforeFilter classFilter) {
			return ArrayUtils.contains(classFilter.except(), invocation.getActionName());
		}

		private boolean hasActionFilters(BeforeFilter filters) {
			return filters.filter() != IActionFilter.class;
		}

		private boolean hasMethodFilters(BeforeFilter filters) {
			return !filters.method().equals("");
		}

		private boolean inclusive(ActionInvocation invocation, BeforeFilter filter) {
			return (filter.only().length == 1 && filter.only()[0].equals("")) || ArrayUtils.contains(filter.only(), invocation.getActionName());
		}
	}
}
