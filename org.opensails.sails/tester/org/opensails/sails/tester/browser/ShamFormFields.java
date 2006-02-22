package org.opensails.sails.tester.browser;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.opensails.sails.adapter.ContainerAdapterResolver;
import org.opensails.sails.form.FormFields;
import org.opensails.sails.form.html.Submit;
import org.opensails.sails.form.html.ValueElement;

/**
 * Used to construct the fields to be submitted in a post or get.
 * 
 * @author aiwilliams
 */
public class ShamFormFields extends FormFields {
	protected final ContainerAdapterResolver adapterResolver;
	protected final List<ValueElement> elements;

	public ShamFormFields(ContainerAdapterResolver adapterResolver) {
		this.adapterResolver = adapterResolver;
		this.elements = new ArrayList<ValueElement>();
	}

	/**
	 * Adds a FileUpload to the FormFields.
	 * 
	 * @param fileFieldName
	 * @param upload
	 */
	public void addFile(String fileFieldName, ShamFileUpload upload) {
		backingMap.put(fileFieldName, upload);
	}

	/**
	 * @see #setSubmitAction(String, List)
	 * @param action
	 */
	public void setSubmitAction(String action) {
		setSubmitAction(action, ListUtils.EMPTY_LIST);
	}

	/**
	 * TODO: Make this useful.
	 * 
	 * Adds a 'meta-submit'.
	 * <p>
	 * This allows you to avoid knowing the details of how Sails provides for
	 * multiple submit buttons on an HTML form. Since there can be only one
	 * action invoked, setting this will overwrite the previous value.
	 * 
	 * @param action
	 */
	public void setSubmitAction(String action, List parameters) {
		add(new Submit("", adapterResolver).action(action, parameters));
	}

	protected <T extends ValueElement> T add(T element) {
		elements.add(element);
		return element;
	}
}
