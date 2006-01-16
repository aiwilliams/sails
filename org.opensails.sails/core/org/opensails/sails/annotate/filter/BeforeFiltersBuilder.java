package org.opensails.sails.annotate.filter;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.apache.commons.lang.ArrayUtils;
import org.opensails.sails.SailsException;
import org.opensails.sails.action.ActionFilters;
import org.opensails.sails.action.ActionMethods;
import org.opensails.sails.action.BeforeFilters;
import org.opensails.sails.action.IActionFilter;
import org.opensails.sails.action.oem.ActionInvocation;
import org.opensails.sails.util.ClassHelper;

public class BeforeFiltersBuilder implements IFilterBuilder<BeforeFilters> {
	static class BeforeFiltersFilter implements IFilter {
		private final BeforeFilters filterDeclaration;
		private final ActionInvocation invocation;

		protected BeforeFiltersFilter(BeforeFilters filterDeclaration, ActionInvocation invocation) {
			this.filterDeclaration = filterDeclaration;
			this.invocation = invocation;
		}

		public void after() {}

		@SuppressWarnings("unchecked")
		public Object before() {
			if (hasActionFilters(filterDeclaration)) {
				for (ActionFilters classFilter : filterDeclaration.actions()) {
					if (!excepted(invocation, classFilter) && inclusive(invocation, classFilter)) {
						for (Class<? extends IActionFilter> filter : classFilter.filters()) {
							IActionFilter actionFilter = ClassHelper.instantiate(filter);
							if (!actionFilter.beforeAction(invocation.getAction(), invocation.getContext())) return false;
						}
					}
				}
			}
			if (hasMethodFilters(filterDeclaration)) {
				for (ActionMethods methodFilter : filterDeclaration.methods()) {
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

	public IFilter build(BeforeFilters filterDeclaration, ActionInvocation invocation) {
		return new BeforeFiltersFilter(filterDeclaration, invocation);
	}
}
