package org.opensails.sails.tester.form;

import java.util.ArrayList;
import java.util.List;

import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.form.html.Submit;
import org.opensails.sails.form.html.ValueElement;

public class TestFormFields extends FormFields {
	protected final ContainerAdapterResolver adapterResolver;
	protected final List<ValueElement> elements;

	public TestFormFields(ContainerAdapterResolver adapterResolver) {
		this.adapterResolver = adapterResolver;
		this.elements = new ArrayList<ValueElement>();
	}

	public Submit submit(String action) {
		return add(new Submit("", adapterResolver).action(action));
	}

	protected <T extends ValueElement> T add(T element) {
		elements.add(element);
		return element;
	}
}
