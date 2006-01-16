package org.opensails.sails.annotate.filter;

import java.lang.annotation.Annotation;

import org.opensails.sails.action.oem.ActionInvocation;

public interface IFilterBuilder<T extends Annotation> {
	IFilter build(T filterDeclaration, ActionInvocation invocation);
}
